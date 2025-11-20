package com.observability.application.usecases;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.exceptions.InvalidLogException;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.ports.output.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateLogEntryUseCase {
    
    private final LogRepository logRepository;
    private final EventPublisher eventPublisher;
    
    public LogEntry execute(LogEntry logEntry) {
        log.debug("Creating log entry for service: {}", logEntry.getService());
        
        // Validate log entry
        if (!logEntry.isValid()) {
            throw new InvalidLogException("Log entry must contain level, message, service, and timestamp");
        }
        
        // Generate ID if not provided
        if (logEntry.getId() == null || logEntry.getId().isBlank()) {
            logEntry = LogEntry.builder()
                .id(UUID.randomUUID().toString())
                .level(logEntry.getLevel())
                .message(logEntry.getMessage())
                .service(logEntry.getService())
                .host(logEntry.getHost())
                .timestamp(logEntry.getTimestamp())
                .tags(logEntry.getTags())
                .traceId(logEntry.getTraceId())
                .spanId(logEntry.getSpanId())
                .metadata(logEntry.getMetadata())
                .build();
        }
        
        // Save log entry
        LogEntry savedLogEntry = logRepository.save(logEntry);
        
        // Publish event for real-time updates
        eventPublisher.publishLogEvent(savedLogEntry);
        
        log.debug("Log entry created with ID: {}", savedLogEntry.getId());
        return savedLogEntry;
    }
}

