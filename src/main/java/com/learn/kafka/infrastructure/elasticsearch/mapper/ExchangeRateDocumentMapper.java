package com.learn.kafka.infrastructure.elasticsearch.mapper;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import com.learn.kafka.infrastructure.elasticsearch.document.ExchangeRateDocument;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExchangeRateDocumentMapper {

    public List<ExchangeRateDocument> toDocuments(ExchangeRateMessage message) {
        Instant now = Instant.now();

        return message.getRates().entrySet().stream()
                .map(entry -> toDocument(message.getBase(), entry, now))
                .collect(Collectors.toList());
    }

    private ExchangeRateDocument toDocument(String base,
                                            Map.Entry<String, Double> entry, // ✅ FIX
                                            Instant timestamp) {

        String id = base + "-" + entry.getKey() + "-" + timestamp.toEpochMilli();

        return new ExchangeRateDocument(
                id,
                base,
                entry.getKey(),
                entry.getValue(), // ✅ Double direct
                timestamp
        );
    }
}