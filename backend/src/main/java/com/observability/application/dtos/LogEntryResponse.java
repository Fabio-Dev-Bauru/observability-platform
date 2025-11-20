package com.observability.application.dtos;

import com.observability.domain.valueobjects.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * DTO for log entry response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntryResponse {
    
    private String id;
    private LogLevel level;
    private String message;
    private String service;
    private String host;
    private Instant timestamp;
    private Map<String, String> tags;
    private String traceId;
    private String spanId;
    private Map<String, Object> metadata;
}

