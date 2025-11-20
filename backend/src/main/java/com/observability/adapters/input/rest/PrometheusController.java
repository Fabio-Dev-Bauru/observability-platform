package com.observability.adapters.input.rest;

import com.observability.application.dtos.MetricResponse;
import com.observability.application.dtos.PrometheusImportRequest;
import com.observability.application.mappers.MetricMapper;
import com.observability.application.usecases.ImportPrometheusMetricsUseCase;
import com.observability.domain.entities.Metric;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/prometheus")
@RequiredArgsConstructor
public class PrometheusController {
    
    private final ImportPrometheusMetricsUseCase importPrometheusMetricsUseCase;
    private final MetricMapper metricMapper;
    
    @PostMapping("/import")
    public ResponseEntity<List<MetricResponse>> importMetrics(@Valid @RequestBody PrometheusImportRequest request) {
        log.info("Received Prometheus metrics import request");
        
        List<Metric> metrics = importPrometheusMetricsUseCase.execute(request.getData());
        List<MetricResponse> responses = metrics.stream()
            .map(metricMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
    
    @PostMapping("/metrics")
    public ResponseEntity<String> receivePrometheusMetrics(@RequestBody String prometheusData) {
        log.debug("Received Prometheus metrics via /metrics endpoint");
        
        try {
            importPrometheusMetricsUseCase.execute(prometheusData);
            return ResponseEntity.ok("Metrics imported successfully");
        } catch (Exception e) {
            log.error("Failed to import Prometheus metrics: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Failed to import metrics: " + e.getMessage());
        }
    }
}

