package com.observability.application.mappers;

import com.observability.application.dtos.LogEntryRequest;
import com.observability.application.dtos.LogEntryResponse;
import com.observability.domain.entities.LogEntry;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Mapper for converting between LogEntry entities and DTOs.
 */
@Component
public class LogEntryMapper {
    
    /**
     * Converts a LogEntryRequest to a LogEntry entity.
     * 
     * @param request The request DTO
     * @return The log entry entity
     */
    public LogEntry toEntity(LogEntryRequest request) {
        return LogEntry.builder()
            .level(request.getLevel())
            .message(request.getMessage())
            .service(request.getService())
            .host(request.getHost())
            .timestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now())
            .tags(request.getTags())
            .traceId(request.getTraceId())
            .spanId(request.getSpanId())
            .metadata(request.getMetadata())
            .build();
    }
    
    /**
     * Converts a LogEntry entity to a LogEntryResponse.
     * 
     * @param logEntry The log entry entity
     * @return The response DTO
     */
    public LogEntryResponse toResponse(LogEntry logEntry) {
        return LogEntryResponse.builder()
            .id(logEntry.getId())
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
}

