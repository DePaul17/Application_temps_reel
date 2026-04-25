package com.learn.kafka.api.mapper;

import com.learn.kafka.api.dto.ExchangeRateResponseDto;
import com.learn.kafka.domain.model.ExchangeRateMessage;
import org.springframework.stereotype.Component;

/**
 * Convertit le modèle interne {@link ExchangeRateMessage}
 * en DTO exposé par l'API REST {@link ExchangeRateResponseDto}.
 *
 * Permet de ne jamais exposer le modèle interne directement en HTTP.
 */
@Component
public class ExchangeRateMapper {

    public ExchangeRateResponseDto toDto(ExchangeRateMessage message) {
        if (message == null) {
            return new ExchangeRateResponseDto(null, null, "NO_DATA");
        }
        return new ExchangeRateResponseDto(message.getBase(), message.getRates(), "OK");
    }
}
