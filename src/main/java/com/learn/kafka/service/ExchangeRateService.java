package com.learn.kafka.service;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import com.learn.kafka.infrastructure.client.ExchangeRateApiClient;
import com.learn.kafka.infrastructure.kafka.producer.ExchangeRateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service d'orchestration principal.
 *
 * Coordonne l'appel à l'API externe ({@link ExchangeRateApiClient})
 * et la publication sur Kafka ({@link ExchangeRateProducer}).
 *
 * Ne contient aucune logique d'infrastructure directe.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateApiClient apiClient;
    private final ExchangeRateProducer producer;

    /**
     * Récupère les taux depuis l'API externe ET publie sur Kafka.
     *
     * @return le message publié, ou {@code null} si l'API est indisponible
     */
    public ExchangeRateMessage fetchAndPublish() {
        ExchangeRateMessage message = apiClient.fetchLatestRates();
        if (message == null) {
            log.error("Aucun taux récupéré — publication Kafka annulée");
            return null;
        }
        producer.send(message);
        return message;
    }

    /**
     * Récupère les taux depuis l'API externe SANS publier sur Kafka.
     * Utilisé pour consulter les taux en temps réel via l'API REST.
     *
     * @return le message avec les taux, ou {@code null} si l'API est indisponible
     */
    public ExchangeRateMessage fetchOnly() {
        return apiClient.fetchLatestRates();
    }
}
