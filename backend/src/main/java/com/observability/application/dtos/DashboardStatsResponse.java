package com.observability.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    
    private Long totalLogs;
    private Long totalLogsLastHour;
    private Long errorLogs;
    private Double errorRate;
    private Long activeAlerts;
    private Long totalMetrics;
    private Map<String, Long> logsByLevel;
}

