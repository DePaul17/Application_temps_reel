package com.learn.kafka.scheduler;

import com.learn.kafka.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Déclencheur périodique du pipeline de taux de change.
 *
 * Toutes les {@code app.scheduler.fixed-rate-ms} millisecondes (défaut : 5 min),
 * il demande à {@link ExchangeRateService} de récupérer et publier les taux.
 *
 * Le scheduler peut être désactivé via {@code app.scheduler.enabled=false}
 * (utile en environnement de test ou de debug).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeRateService service;

    @Value("${app.scheduler.enabled:true}")
    private boolean enabled;

    @Scheduled(fixedRateString = "${app.scheduler.fixed-rate-ms:300000}")
    public void run() {
        if (!enabled) {
            log.debug("Scheduler désactivé — cycle ignoré");
            return;
        }

        log.info("Scheduler déclenché — récupération et publication des taux");

        var result = service.fetchAndPublish();

        if (result == null) {
            log.error("Cycle échoué — aucun taux récupéré");
        } else {
            log.info("Cycle terminé avec succès — base='{}'", result.getBase());
        }
    }
}
