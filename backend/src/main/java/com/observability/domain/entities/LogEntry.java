package com.observability.domain.entities;

import com.observability.domain.valueobjects.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a log entry in the system.
 * 
 * This entity contains all the information about a single log entry,
 * including its level, message, metadata, and source information.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {
    
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
    
    /**
     * Validates if the log entry has all required fields.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return level != null 
            && message != null && !message.isBlank()
            && service != null && !service.isBlank()
            && timestamp != null;
    }
}

