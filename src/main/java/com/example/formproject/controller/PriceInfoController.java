package com.example.formproject.controller;

import com.example.formproject.dto.request.PriceInfoRequestDto2;
import com.example.formproject.dto.response.PriceInfoResponseDto;
import com.example.formproject.entity.Crop;
import com.example.formproject.repository.CropRepository;
import com.example.formproject.security.MemberDetail;
import com.example.formproject.service.PriceInfoService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PriceInfoController {

    private final PriceInfoService priceInfoService;
    private final CropRepository cropRepository;

    @PostMapping("/marketprice")
    public PriceInfoResponseDto PriceInfo(@RequestBody PriceInfoRequestDto2 priceInfoRequestDto2,
                                          @AuthenticationPrincipal MemberDetail memberdetail) throws IOException, ParseException {
        //정보 받은걸 여기서 풀어서 통합 DTO로 만들고 넘겨준다.
        Crop crop = cropRepository.findById(priceInfoRequestDto2.getCropId()).orElseThrow();
        System.out.println(crop.getCategory());
        System.out.println(crop.getType());
        System.out.println(crop.getKind());
        PriceInfoResponseDto priceInfoResponseDto = priceInfoService.priceInfo(priceInfoRequestDto2, memberdetail);
        return priceInfoResponseDto;
    }
}
