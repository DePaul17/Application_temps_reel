package com.learn.kafka.infrastructure.kafka.producer;

import com.learn.kafka.domain.model.ExchangeRateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateProducer {

    private final KafkaTemplate<String, ExchangeRateMessage> kafkaTemplate;

    @Value("${app.kafka.messages-topic}")
    private String topic;

    public void send(ExchangeRateMessage message) {

        CompletableFuture<SendResult<String, ExchangeRateMessage>> future =
                kafkaTemplate.send(topic, message.getBase(), message);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("❌ Kafka error for base '{}': {}",
                        message.getBase(),
                        ex.getMessage());
            } else {
                log.info("✅ Kafka sent → topic={}, partition={}, offset={}",
                        topic,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}