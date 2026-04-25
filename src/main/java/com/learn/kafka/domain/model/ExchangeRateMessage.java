package com.learn.kafka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateMessage {

    /** Devise de base (ex: "USD") */
    private String base;

    /** Map devise cible → taux */
    private Map<String, Double> rates;
}