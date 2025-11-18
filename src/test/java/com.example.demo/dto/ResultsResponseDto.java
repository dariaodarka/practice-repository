package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultsResponseDto {
    private String userPromoCode;
    private List<ResultItemDto> results;
}