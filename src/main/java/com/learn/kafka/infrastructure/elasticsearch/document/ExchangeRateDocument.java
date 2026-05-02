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
 *
 * Chaque document correspond à une paire de devises (ex: USD/EUR)
 * à un instant précis.
 *
 * Ce document est utilisé pour :
 * - Stocker les données dans Elasticsearch
 * - Permettre des recherches rapides (filtre par devise, date, etc.)
 * - Alimenter les visualisations dans Kibana
 *
 * Index : "exchange-rates"
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "exchange-rates")
public class ExchangeRateDocument {

    /**
     * Identifiant unique du document.
     * Généralement construit avec base + target + timestamp
     * pour éviter les doublons.
     */
    @Id
    private String id;

    /**
     * Devise de base (ex: USD).
     * Type Keyword = non analysé → parfait pour les filtres exacts.
     */
    @Field(type = FieldType.Keyword)
    private String baseCurrency;

    /**
     * Devise cible (ex: EUR).
     * Utilisé pour les recherches et agrégations.
     */
    @Field(type = FieldType.Keyword)
    private String targetCurrency;

    /**
     * Taux de conversion entre la devise de base et la devise cible.
     */
    @Field(type = FieldType.Double)
    private double rate;

    /**
     * Date et heure de récupération du taux.
     *
     * Format ISO 8601 en UTC pour :
     * - assurer la compatibilité avec Elasticsearch
     * - éviter les problèmes de fuseaux horaires
     * - faciliter les visualisations temporelles dans Kibana
     */
    @Field(type = FieldType.Date)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC"
    )
    private Instant timestamp;
}

