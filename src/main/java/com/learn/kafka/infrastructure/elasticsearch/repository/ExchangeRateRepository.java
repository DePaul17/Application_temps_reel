package com.learn.kafka.infrastructure.elasticsearch.repository;

import com.learn.kafka.infrastructure.elasticsearch.document.ExchangeRateDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Repository Spring Data Elasticsearch pour les taux de change.
 *
 * Les opérations CRUD de base sont fournies par {@link ElasticsearchRepository}.
 * Ajouter ici les méthodes de requête métier si nécessaire.
 *
 * Exemples d'extensions futures :
 *   List<ExchangeRateDocument> findByBaseCurrencyAndTargetCurrency(String base, String target);
 *   List<ExchangeRateDocument> findByTimestampBetween(Instant from, Instant to);
 */
public interface ExchangeRateRepository
        extends ElasticsearchRepository<ExchangeRateDocument, String> {
}
