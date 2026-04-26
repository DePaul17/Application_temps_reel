package com.learn.kafka.api.controller;

import com.learn.kafka.api.dto.ExchangeRateResponseDto;
import com.learn.kafka.api.mapper.ExchangeRateMapper;
import com.learn.kafka.domain.model.ExchangeRateMessage;
import com.learn.kafka.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Point d'entrée HTTP pour les taux de change.
 *
 * GET  /exchange-rates          → récupère les taux depuis l'API externe (sans Kafka)
 * POST /exchange-rates/publish  → récupère ET publie sur Kafka (déclenchement manuel)
 */
@RestController
@RequestMapping("/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService service;
    private final ExchangeRateMapper mapper;

    @GetMapping
    public ResponseEntity<ExchangeRateResponseDto> getRates() {
        ExchangeRateMessage message = service.fetchOnly();
        return ResponseEntity.ok(mapper.toDto(message));
    }

    @PostMapping("/publish")
    public ResponseEntity<ExchangeRateResponseDto> publishRates() {
        ExchangeRateMessage message = service.fetchAndPublish();
        return ResponseEntity.ok(mapper.toDto(message));
    }
}
