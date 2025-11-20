package com.observability.application.usecases;

import com.observability.domain.entities.Alert;
import com.observability.domain.exceptions.InvalidAlertException;
import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.ports.output.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAlertUseCase {
    
    private final AlertRepository alertRepository;
    private final EventPublisher eventPublisher;
    
    public Alert execute(Alert alert) {
        log.debug("Creating alert: {}", alert.getName());
        
        if (!alert.isValid()) {
            throw new InvalidAlertException("Alert must contain name, severity, status, and triggeredAt");
        }
        
        if (alert.getId() == null || alert.getId().isBlank()) {
            alert = Alert.builder()
                .id(UUID.randomUUID().toString())
                .name(alert.getName())
                .description(alert.getDescription())
                .severity(alert.getSeverity())
                .status(alert.getStatus())
                .service(alert.getService())
                .triggeredAt(alert.getTriggeredAt())
                .resolvedAt(alert.getResolvedAt())
                .metadata(alert.getMetadata())
                .ruleId(alert.getRuleId())
                .build();
        }
        
        Alert savedAlert = alertRepository.save(alert);
        
        eventPublisher.publishAlertEvent(savedAlert);
        
        log.debug("Alert created with ID: {}", savedAlert.getId());
        return savedAlert;
    }
}

