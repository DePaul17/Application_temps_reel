package com.learn.kafka.infrastructure.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * Document Elasticsearch représentant un taux de change unique.
 * Chaque document = 1 paire de devises à un instant donné.
 *
 * Index : "exchange-rates"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "exchange-rates")
public class ExchangeRateDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String baseCurrency;

    @Field(type = FieldType.Keyword)
    private String targetCurrency;

    @Field(type = FieldType.Double)
    private double rate;

    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;
}
