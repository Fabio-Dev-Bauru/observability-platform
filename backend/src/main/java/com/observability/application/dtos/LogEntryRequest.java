package com.observability.application.dtos;

import com.observability.domain.valueobjects.LogLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * DTO for creating a log entry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntryRequest {
    
    @NotNull(message = "Log level is required")
    private LogLevel level;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotBlank(message = "Service is required")
    private String service;
    
    private String host;
    
    private Instant timestamp;
    
    private Map<String, String> tags;
    
    private String traceId;
    
    private String spanId;
    
    private Map<String, Object> metadata;
}

