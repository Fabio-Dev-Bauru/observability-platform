package com.observability.adapters.input.rest;

import com.observability.application.dtos.LogEntryRequest;
import com.observability.application.dtos.LogEntryResponse;
import com.observability.application.mappers.LogEntryMapper;
import com.observability.application.usecases.CreateLogEntryUseCase;
import com.observability.application.usecases.GetLogsUseCase;
import com.observability.domain.entities.LogEntry;
import com.observability.domain.valueobjects.LogLevel;
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
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {
    
    private final CreateLogEntryUseCase createLogEntryUseCase;
    private final GetLogsUseCase getLogsUseCase;
    private final LogEntryMapper logEntryMapper;
    
    @PostMapping
    public ResponseEntity<LogEntryResponse> createLogEntry(@Valid @RequestBody LogEntryRequest request) {
        log.info("Received request to create log entry for service: {}", request.getService());
        
        LogEntry logEntry = logEntryMapper.toEntity(request);
        LogEntry createdLogEntry = createLogEntryUseCase.execute(logEntry);
        LogEntryResponse response = logEntryMapper.toResponse(createdLogEntry);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<LogEntryResponse>> getLogs(
        @RequestParam(required = false) LogLevel level,
        @RequestParam(required = false) String service,
        @RequestParam(required = false) String host,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.debug("Retrieving logs with filters - level: {}, service: {}, host: {}", level, service, host);
        
        List<LogEntry> logEntries = getLogsUseCase.execute(level, service, host, startTime, endTime, page, size);
        List<LogEntryResponse> responses = logEntries.stream()
            .map(logEntryMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Logs service is healthy");
    }
}

