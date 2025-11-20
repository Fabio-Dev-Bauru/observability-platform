package com.observability.application.usecases;

import com.observability.domain.ports.output.AlertRepository;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.ports.output.MetricRepository;
import com.observability.domain.valueobjects.AlertStatus;
import com.observability.domain.valueobjects.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetDashboardStatsUseCase {
    
    private final LogRepository logRepository;
    private final MetricRepository metricRepository;
    private final AlertRepository alertRepository;
    
    public Map<String, Object> execute() {
        Instant now = Instant.now();
        Instant oneHourAgo = now.minusSeconds(3600);
        Instant oneDayAgo = now.minusSeconds(86400);
        
        long totalLogs = logRepository.countByFilters(null, null, null, oneDayAgo, now);
        long errorLogs = logRepository.countByFilters(LogLevel.ERROR, null, null, oneHourAgo, now);
        long totalLogsLastHour = logRepository.countByFilters(null, null, null, oneHourAgo, now);
        
        double errorRate = totalLogsLastHour > 0 
            ? (double) errorLogs / totalLogsLastHour * 100 
            : 0.0;
        
        long activeAlerts = alertRepository.findActiveAlerts().size();
        long totalMetrics = metricRepository.findByFilters(null, null, null, oneHourAgo, now, 0, 1000).size();
        
        Map<String, Long> logsByLevel = new HashMap<>();
        for (LogLevel level : LogLevel.values()) {
            long count = logRepository.countByFilters(level, null, null, oneHourAgo, now);
            logsByLevel.put(level.name(), count);
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLogs", totalLogs);
        stats.put("totalLogsLastHour", totalLogsLastHour);
        stats.put("errorLogs", errorLogs);
        stats.put("errorRate", Math.round(errorRate * 100.0) / 100.0);
        stats.put("activeAlerts", activeAlerts);
        stats.put("totalMetrics", totalMetrics);
        stats.put("logsByLevel", logsByLevel);
        
        return stats;
    }
}

