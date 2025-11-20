package com.observability.application.usecases;

import com.observability.domain.entities.Alert;
import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAlertsUseCase {
    
    private final AlertRepository alertRepository;
    
    public List<Alert> execute(
        AlertStatus status,
        AlertSeverity severity,
        String service,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        log.debug("Retrieving alerts with filters - status: {}, severity: {}, service: {}", status, severity, service);
        
        if (startTime == null) {
            startTime = Instant.now().minusSeconds(86400);
        }
        if (endTime == null) {
            endTime = Instant.now();
        }
        
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20;
        }
        
        return alertRepository.findByFilters(status, severity, service, startTime, endTime, page, size);
    }
    
    public List<Alert> getActiveAlerts() {
        return alertRepository.findActiveAlerts();
    }
}

