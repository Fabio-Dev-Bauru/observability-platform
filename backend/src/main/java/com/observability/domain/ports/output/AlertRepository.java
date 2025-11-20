package com.observability.domain.ports.output;

import com.observability.domain.entities.Alert;
import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AlertRepository {
    
    Alert save(Alert alert);
    
    Optional<Alert> findById(String id);
    
    List<Alert> findByFilters(
        AlertStatus status,
        AlertSeverity severity,
        String service,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    );
    
    List<Alert> findActiveAlerts();
}

