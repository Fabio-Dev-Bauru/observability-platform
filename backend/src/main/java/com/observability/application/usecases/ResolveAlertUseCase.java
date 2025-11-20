package com.observability.application.usecases;

import com.observability.domain.entities.Alert;
import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.ports.output.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResolveAlertUseCase {
    
    private final AlertRepository alertRepository;
    private final EventPublisher eventPublisher;
    
    public Alert execute(String alertId) {
        log.debug("Resolving alert: {}", alertId);
        
        Alert alert = alertRepository.findById(alertId)
            .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));
        
        alert.resolve();
        Alert resolvedAlert = alertRepository.save(alert);
        
        eventPublisher.publishAlertEvent(resolvedAlert);
        
        log.debug("Alert resolved: {}", alertId);
        return resolvedAlert;
    }
}

