package com.observability.application.mappers;

import com.observability.application.dtos.AlertRequest;
import com.observability.application.dtos.AlertResponse;
import com.observability.domain.entities.Alert;
import com.observability.domain.valueobjects.AlertStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AlertMapper {
    
    public Alert toEntity(AlertRequest request) {
        Alert alert = Alert.builder()
            .name(request.getName())
            .description(request.getDescription())
            .severity(request.getSeverity())
            .service(request.getService())
            .metadata(request.getMetadata())
            .ruleId(request.getRuleId())
            .build();
        
        alert.activate();
        return alert;
    }
    
    public AlertResponse toResponse(Alert alert) {
        return AlertResponse.builder()
            .id(alert.getId())
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
}

