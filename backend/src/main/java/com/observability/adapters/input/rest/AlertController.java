package com.observability.adapters.input.rest;

import com.observability.application.dtos.AlertRequest;
import com.observability.application.dtos.AlertResponse;
import com.observability.application.mappers.AlertMapper;
import com.observability.application.usecases.CreateAlertUseCase;
import com.observability.application.usecases.GetAlertsUseCase;
import com.observability.application.usecases.ResolveAlertUseCase;
import com.observability.domain.entities.Alert;
import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
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
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {
    
    private final CreateAlertUseCase createAlertUseCase;
    private final GetAlertsUseCase getAlertsUseCase;
    private final ResolveAlertUseCase resolveAlertUseCase;
    private final AlertMapper alertMapper;
    
    @PostMapping
    public ResponseEntity<AlertResponse> createAlert(@Valid @RequestBody AlertRequest request) {
        log.info("Received request to create alert: {}", request.getName());
        
        Alert alert = alertMapper.toEntity(request);
        Alert createdAlert = createAlertUseCase.execute(alert);
        AlertResponse response = alertMapper.toResponse(createdAlert);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAlerts(
        @RequestParam(required = false) AlertStatus status,
        @RequestParam(required = false) AlertSeverity severity,
        @RequestParam(required = false) String service,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.debug("Retrieving alerts with filters - status: {}, severity: {}, service: {}", status, severity, service);
        
        List<Alert> alerts = getAlertsUseCase.execute(status, severity, service, startTime, endTime, page, size);
        List<AlertResponse> responses = alerts.stream()
            .map(alertMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<AlertResponse>> getActiveAlerts() {
        List<Alert> alerts = getAlertsUseCase.getActiveAlerts();
        List<AlertResponse> responses = alerts.stream()
            .map(alertMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}/resolve")
    public ResponseEntity<AlertResponse> resolveAlert(@PathVariable String id) {
        log.info("Resolving alert: {}", id);
        
        Alert resolvedAlert = resolveAlertUseCase.execute(id);
        AlertResponse response = alertMapper.toResponse(resolvedAlert);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Alerts service is healthy");
    }
}

