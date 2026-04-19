package com.learn.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    // Adresse du broker Kafka lue depuis application.properties
    private final String bootstrapServers;

    // Nom du groupe de consommation pour gerer les offsets
    private final String consumerGroupId;

    public KafkaConsumerConfig(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String consumerGroupId
    ) {
        this.bootstrapServers = bootstrapServers;
        this.consumerGroupId = consumerGroupId;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Configuration minimale pour lire des messages texte
        Map<String, Object> configProps = new HashMap<>();

        // Serveur Kafka cible
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Groupe qui partage la lecture des messages
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);

        // Le consumer lit des cles et valeurs de type String
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        // Fabrique des listeners utilises par @KafkaListener
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
