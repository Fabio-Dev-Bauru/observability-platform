package com.observability.adapters.input.rest;

import com.observability.application.dtos.MetricRequest;
import com.observability.application.dtos.MetricResponse;
import com.observability.application.mappers.MetricMapper;
import com.observability.application.usecases.CreateMetricUseCase;
import com.observability.application.usecases.GetMetricsUseCase;
import com.observability.domain.entities.Metric;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricController {
    
    private final CreateMetricUseCase createMetricUseCase;
    private final GetMetricsUseCase getMetricsUseCase;
    private final MetricMapper metricMapper;
    
    @PostMapping
    public ResponseEntity<MetricResponse> createMetric(@Valid @RequestBody MetricRequest request) {
        log.info("Received request to create metric: {}", request.getName());
        
        Metric metric = metricMapper.toEntity(request);
        Metric createdMetric = createMetricUseCase.execute(metric);
        MetricResponse response = metricMapper.toResponse(createdMetric);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<MetricResponse>> getMetrics(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String service,
        @RequestParam(required = false) String host,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.debug("Retrieving metrics with filters - name: {}, service: {}, host: {}", name, service, host);
        
        List<Metric> metrics = getMetricsUseCase.execute(name, service, host, startTime, endTime, page, size);
        List<MetricResponse> responses = metrics.stream()
            .map(metricMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Metrics service is healthy");
    }
}

