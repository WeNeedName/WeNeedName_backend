package com.example.formproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceInfoRequestDto2 {
    private int cropId;
    private String productClsCode;
    private String gradeRank;
    private String data;
}
