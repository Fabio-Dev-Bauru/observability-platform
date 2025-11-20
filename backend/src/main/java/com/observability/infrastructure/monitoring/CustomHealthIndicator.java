package com.observability.infrastructure.monitoring;

import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.ports.output.MetricRepository;
import com.observability.domain.valueobjects.AlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {
    
    private final LogRepository logRepository;
    private final MetricRepository metricRepository;
    private final AlertRepository alertRepository;
    
    @Override
    public Health health() {
        try {
            long logCount = logRepository.countByFilters(
                null, null, null,
                Instant.now().minusSeconds(3600),
                Instant.now()
            );
            
            long activeAlerts = alertRepository.findActiveAlerts().size();
            
            Health.Builder status = Health.up()
                .withDetail("logs.lastHour", logCount)
                .withDetail("activeAlerts", activeAlerts)
                .withDetail("timestamp", Instant.now().toString());
            
            if (activeAlerts > 10) {
                status.status("DEGRADED")
                    .withDetail("warning", "High number of active alerts");
            }
            
            return status.build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}

