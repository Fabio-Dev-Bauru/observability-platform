package com.observability.adapters.output.persistence;

import com.observability.domain.entities.Alert;
import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlertRepositoryAdapter implements AlertRepository {
    
    private final AlertJpaRepository jpaRepository;
    
    @Override
    public Alert save(Alert alert) {
        AlertEntity entity = toEntity(alert);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
        }
        AlertEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Alert> findById(String id) {
        return jpaRepository.findById(id)
            .map(this::toDomain);
    }
    
    @Override
    public List<Alert> findByFilters(
        AlertStatus status,
        AlertSeverity severity,
        String service,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        String statusStr = status != null ? status.name() : null;
        String severityStr = severity != null ? severity.name() : null;
        
        return jpaRepository.findByFilters(statusStr, severityStr, service, startTime, endTime, pageable)
            .getContent()
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Alert> findActiveAlerts() {
        return jpaRepository.findByStatus(AlertStatus.ACTIVE.name())
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    private AlertEntity toEntity(Alert alert) {
        return AlertEntity.builder()
            .id(alert.getId())
            .name(alert.getName())
            .description(alert.getDescription())
            .severity(alert.getSeverity().name())
            .status(alert.getStatus().name())
            .service(alert.getService())
            .triggeredAt(alert.getTriggeredAt())
            .resolvedAt(alert.getResolvedAt())
            .metadata(alert.getMetadata())
            .ruleId(alert.getRuleId())
            .build();
    }
    
    private Alert toDomain(AlertEntity entity) {
        return Alert.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .severity(AlertSeverity.valueOf(entity.getSeverity()))
            .status(AlertStatus.valueOf(entity.getStatus()))
            .service(entity.getService())
            .triggeredAt(entity.getTriggeredAt())
            .resolvedAt(entity.getResolvedAt())
            .metadata(entity.getMetadata())
            .ruleId(entity.getRuleId())
            .build();
    }
}

