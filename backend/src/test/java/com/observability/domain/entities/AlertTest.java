package com.observability.domain.entities;

import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlertTest {
    
    @Test
    void shouldCreateValidAlert() {
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertTrue(alert.isValid());
        assertEquals("High CPU Usage", alert.getName());
        assertEquals(AlertSeverity.HIGH, alert.getSeverity());
        assertEquals(AlertStatus.ACTIVE, alert.getStatus());
    }
    
    @Test
    void shouldBeInvalidWhenNameIsNull() {
        Alert alert = Alert.builder()
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertFalse(alert.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenNameIsBlank() {
        Alert alert = Alert.builder()
            .name("")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertFalse(alert.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenSeverityIsNull() {
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertFalse(alert.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenStatusIsNull() {
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .triggeredAt(Instant.now())
            .build();
        
        assertFalse(alert.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenTriggeredAtIsNull() {
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .build();
        
        assertFalse(alert.isValid());
    }
    
    @Test
    void shouldResolveAlert() {
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.ACTIVE)
            .triggeredAt(Instant.now())
            .build();
        
        assertNull(alert.getResolvedAt());
        
        alert.resolve();
        
        assertEquals(AlertStatus.RESOLVED, alert.getStatus());
        assertNotNull(alert.getResolvedAt());
    }
    
    @Test
    void shouldActivateAlert() {
        Instant oldTriggeredAt = Instant.now().minusSeconds(3600);
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .severity(AlertSeverity.HIGH)
            .status(AlertStatus.RESOLVED)
            .triggeredAt(oldTriggeredAt)
            .resolvedAt(Instant.now().minusSeconds(1800))
            .build();
        
        assertEquals(AlertStatus.RESOLVED, alert.getStatus());
        assertEquals(oldTriggeredAt, alert.getTriggeredAt());
        
        alert.activate();
        
        assertEquals(AlertStatus.ACTIVE, alert.getStatus());
        assertNotNull(alert.getTriggeredAt());
        assertNotEquals(oldTriggeredAt, alert.getTriggeredAt());
    }
    
    @Test
    void shouldAcceptOptionalFields() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("threshold", 80.0);
        metadata.put("currentValue", 95.5);
        
        Alert alert = Alert.builder()
            .name("High CPU Usage")
            .description("CPU usage exceeded threshold")
            .severity(AlertSeverity.CRITICAL)
            .status(AlertStatus.ACTIVE)
            .service("api-service")
            .triggeredAt(Instant.now())
            .metadata(metadata)
            .ruleId("rule-123")
            .build();
        
        assertTrue(alert.isValid());
        assertEquals("CPU usage exceeded threshold", alert.getDescription());
        assertEquals("api-service", alert.getService());
        assertEquals(metadata, alert.getMetadata());
        assertEquals("rule-123", alert.getRuleId());
    }
}

