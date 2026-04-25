package com.learn.kafka.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponseDto {

    private String base;

    // ✅ CORRECTION ICI
    private Map<String, Double> rates;

    private String status;
}