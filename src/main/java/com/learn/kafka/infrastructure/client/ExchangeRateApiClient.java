package com.learn.kafka.infrastructure.client;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateApiClient {

    private final RestTemplate restTemplate;

    @Value("${app.exchange-api.url}")
    private String apiUrl;

    @SuppressWarnings("unchecked")
    public ExchangeRateMessage fetchLatestRates() {
        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

            if (response == null || response.get("rates") == null) {
                log.warn("Réponse vide ou sans rates depuis l'API : {}", apiUrl);
                return null;
            }

            String base = (String) response.get("base");
            Map<String, Object> rawRates = (Map<String, Object>) response.get("rates");
            Map<String, Double> cleanRates = parseRates(rawRates);

            return new ExchangeRateMessage(base, cleanRates);

        } catch (RestClientException e) {
            log.error("Erreur lors de l'appel à l'API externe [{}] : {}", apiUrl, e.getMessage());
            return null;
        }
    }

    private Map<String, Double> parseRates(Map<String, Object> rawRates) {
        Map<String, Double> cleanRates = new HashMap<>();
        rawRates.forEach((currency, value) -> {
            if (value instanceof Number number) {
                cleanRates.put(currency, number.doubleValue());
            } else if (value instanceof String str) {
                try {
                    cleanRates.put(currency, Double.parseDouble(str.replace(",", ".")));
                } catch (NumberFormatException e) {
                    log.warn("Taux ignoré (format invalide) : {} -> {}", currency, str);
                }
            }
        });
        return cleanRates;
    }
}