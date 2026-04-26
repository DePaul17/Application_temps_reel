package com.learn.kafka.service;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import com.learn.kafka.infrastructure.elasticsearch.document.ExchangeRateDocument;
import com.learn.kafka.infrastructure.elasticsearch.mapper.ExchangeRateDocumentMapper;
import com.learn.kafka.infrastructure.elasticsearch.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsable de la persistance des taux dans Elasticsearch.
 *
 * Reçoit un {@link ExchangeRateMessage} depuis le consumer Kafka,
 * le convertit en documents via le mapper, et les sauvegarde en batch.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateIndexingService {

    private final ExchangeRateRepository repository;
    private final ExchangeRateDocumentMapper mapper;

    /**
     * Indexe tous les taux d'un message dans Elasticsearch.
     *
     * @param message le message à indexer (peut être {@code null} ou vide)
     */
    public void index(ExchangeRateMessage message) {
        if (message == null || message.getRates() == null || message.getRates().isEmpty()) {
            log.warn("Message invalide ou vide — indexation ignorée");
            return;
        }

        List<ExchangeRateDocument> documents = mapper.toDocuments(message);
        repository.saveAll(documents);

        log.info("Indexation Elasticsearch terminée → base='{}', {} documents",
                message.getBase(), documents.size());
    }
}
