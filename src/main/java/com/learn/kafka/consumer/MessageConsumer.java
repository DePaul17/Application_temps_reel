package com.learn.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

    // Ecoute le meme topic que celui utilise par le producer REST
    @KafkaListener(topics = "${app.kafka.messages-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        // Affiche le message recu pour verifier la consommation
        log.info("Message consommé depuis Kafka : {}", message);
    }

}