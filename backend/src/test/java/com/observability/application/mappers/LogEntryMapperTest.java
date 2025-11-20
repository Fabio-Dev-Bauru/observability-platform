package com.observability.application.mappers;

import com.observability.application.dtos.LogEntryRequest;
import com.observability.application.dtos.LogEntryResponse;
import com.observability.domain.entities.LogEntry;
import com.observability.domain.valueobjects.LogLevel;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryMapperTest {
    
    private final LogEntryMapper mapper = new LogEntryMapper();
    
    @Test
    void shouldMapRequestToEntity() {
        LogEntryRequest request = LogEntryRequest.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("test-service")
            .host("localhost")
            .build();
        
        LogEntry entity = mapper.toEntity(request);
        
        assertEquals(LogLevel.INFO, entity.getLevel());
        assertEquals("Test message", entity.getMessage());
        assertEquals("test-service", entity.getService());
        assertEquals("localhost", entity.getHost());
        assertNotNull(entity.getTimestamp());
    }
    
    @Test
    void shouldUseCurrentTimestampWhenNotProvided() {
        LogEntryRequest request = LogEntryRequest.builder()
            .level(LogLevel.INFO)
            .message("Test message")
            .service("test-service")
            .build();
        
        LogEntry entity = mapper.toEntity(request);
        
        assertNotNull(entity.getTimestamp());
        assertTrue(entity.getTimestamp().isBefore(Instant.now().plusSeconds(1)));
    }
    
    @Test
    void shouldMapEntityToResponse() {
        Map<String, String> tags = new HashMap<>();
        tags.put("env", "production");
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("userId", "123");
        
        LogEntry entity = LogEntry.builder()
            .id(UUID.randomUUID().toString())
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
        
        LogEntryResponse response = mapper.toResponse(entity);
        
        assertEquals(entity.getId(), response.getId());
        assertEquals(entity.getLevel(), response.getLevel());
        assertEquals(entity.getMessage(), response.getMessage());
        assertEquals(entity.getService(), response.getService());
        assertEquals(entity.getHost(), response.getHost());
        assertEquals(entity.getTimestamp(), response.getTimestamp());
        assertEquals(entity.getTags(), response.getTags());
        assertEquals(entity.getTraceId(), response.getTraceId());
        assertEquals(entity.getSpanId(), response.getSpanId());
        assertEquals(entity.getMetadata(), response.getMetadata());
    }
}

