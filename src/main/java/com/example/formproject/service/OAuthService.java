package com.example.formproject.service;

import com.example.formproject.entity.Member;
import com.example.formproject.repository.MemberRepository;
import com.example.formproject.security.JwtProvider;
import com.example.formproject.security.KakaoOauth;
import com.example.formproject.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final KakaoOauth kakaoOauth;
    private final JwtProvider provider;
    private final MemberRepository memberRepository;
    public String getAccessToken(String code){
        RestTemplate template = new RestTemplate();
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",kakaoOauth.getKAKAO_CLIENT_ID());
        params.add("redirect_uri",kakaoOauth.getREDIRECT_URI());
        params.add("code",code);
        ResponseEntity<Map> response = template.postForEntity(kakaoOauth.getKAKAO_TOKEN_URL(),params,Map.class);
        if(response.getStatusCode() != HttpStatus.OK)
            throw new IllegalArgumentException("코드가 잘못되었습니다.");
        Map<String,Object> ret = response.getBody();
        return ret.get("access_token").toString();
    }
    @Transactional
    public String kakaoLogin(String code){
        String accessToken = getAccessToken(code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Map> response = template.exchange(kakaoOauth.getKAKAO_USER_INFO_URL(),HttpMethod.GET,request,Map.class);
        OAuthAttributes attr = OAuthAttributes.ofKakao(null,response.getBody());
        System.out.println(attr.getName());
        System.out.println(attr.getEmail());
        System.out.println(attr.getPicture());
        Member m = memberRepository.findByEmail(attr.getEmail()).orElse(null);
        if(m == null){
            m = Member.builder()
                    .nickname(attr.getName())
                    .email(attr.getEmail())
                    .build();
        }
        m.updateMember(attr);
        memberRepository.save(m);
        return "Bearer "+provider.generateToken(m, m.getId());
    }
}
