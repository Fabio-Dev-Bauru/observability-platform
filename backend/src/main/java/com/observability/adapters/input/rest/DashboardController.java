package com.observability.adapters.input.rest;

import com.observability.application.dtos.DashboardStatsResponse;
import com.observability.application.usecases.GetDashboardStatsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final GetDashboardStatsUseCase getDashboardStatsUseCase;
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        Map<String, Object> stats = getDashboardStatsUseCase.execute();
        
        DashboardStatsResponse response = DashboardStatsResponse.builder()
            .totalLogs((Long) stats.get("totalLogs"))
            .totalLogsLastHour((Long) stats.get("totalLogsLastHour"))
            .errorLogs((Long) stats.get("errorLogs"))
            .errorRate((Double) stats.get("errorRate"))
            .activeAlerts((Long) stats.get("activeAlerts"))
            .totalMetrics((Long) stats.get("totalMetrics"))
            .logsByLevel((Map<String, Long>) stats.get("logsByLevel"))
            .build();
        
        return ResponseEntity.ok(response);
    }
}

