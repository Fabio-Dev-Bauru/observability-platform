package com.observability.domain.entities;

import com.observability.domain.valueobjects.LogLevel;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryTest {
    
    @Test
    void shouldCreateValidLogEntry() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("test-service")
            .timestamp(Instant.now())
            .build();
        
        assertTrue(logEntry.isValid());
        assertEquals(LogLevel.INFO, logEntry.getLevel());
        assertEquals("Test message", logEntry.getMessage());
        assertEquals("test-service", logEntry.getService());
    }
    
    @Test
    void shouldBeInvalidWhenLevelIsNull() {
        LogEntry logEntry = LogEntry.builder()
            .message("Test message")
            .service("test-service")
            .timestamp(Instant.now())
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenMessageIsNull() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .service("test-service")
            .timestamp(Instant.now())
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenMessageIsBlank() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("")
            .service("test-service")
            .timestamp(Instant.now())
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenServiceIsNull() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .timestamp(Instant.now())
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenServiceIsBlank() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("")
            .timestamp(Instant.now())
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenTimestampIsNull() {
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("test-service")
            .build();
        
        assertFalse(logEntry.isValid());
    }
    
    @Test
    void shouldAcceptOptionalFields() {
        Map<String, String> tags = new HashMap<>();
        tags.put("env", "production");
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("userId", "123");
        
        LogEntry logEntry = LogEntry.builder()
            .level(LogLevel.ERROR)
            .message("Error occurred")
            .service("api-service")
            .host("server-01")
            .timestamp(Instant.now())
            .tags(tags)
            .traceId("trace-123")
            .spanId("span-456")
            .metadata(metadata)
            .build();
        
        assertTrue(logEntry.isValid());
        assertEquals("server-01", logEntry.getHost());
        assertEquals(tags, logEntry.getTags());
        assertEquals("trace-123", logEntry.getTraceId());
        assertEquals("span-456", logEntry.getSpanId());
        assertEquals(metadata, logEntry.getMetadata());
    }
}

