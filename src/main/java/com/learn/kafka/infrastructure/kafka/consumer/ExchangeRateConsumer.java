package com.learn.kafka.infrastructure.kafka.consumer;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import com.learn.kafka.service.ExchangeRateIndexingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateConsumer {

    private final ExchangeRateIndexingService indexingService;

    @KafkaListener(
            topics = "${app.kafka.messages-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ExchangeRateMessage message) {

        log.info("📩 Kafka reçu → base='{}', {} taux",
                message.getBase(),
                message.getRates() != null ? message.getRates().size() : 0);

        if (message.getRates() == null) return;

        message.getRates().forEach((target, rate) -> {
            indexingService.index(
                    message.getBase(),
                    target,
                    rate.doubleValue()
            );
        });
    }
}