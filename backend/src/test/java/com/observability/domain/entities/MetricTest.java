package com.observability.domain.entities;

import com.observability.domain.valueobjects.MetricType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetricTest {
    
    @Test
    void shouldCreateValidMetric() {
        Metric metric = Metric.builder()
            .name("cpu.usage")
            .value(75.5)
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
        
        assertTrue(metric.isValid());
        assertEquals("cpu.usage", metric.getName());
        assertEquals(75.5, metric.getValue());
        assertEquals(MetricType.GAUGE, metric.getType());
    }
    
    @Test
    void shouldBeInvalidWhenNameIsNull() {
        Metric metric = Metric.builder()
            .value(75.5)
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
        
        assertFalse(metric.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenNameIsBlank() {
        Metric metric = Metric.builder()
            .name("")
            .value(75.5)
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
        
        assertFalse(metric.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenValueIsNull() {
        Metric metric = Metric.builder()
            .name("cpu.usage")
            .type(MetricType.GAUGE)
            .timestamp(Instant.now())
            .build();
        
        assertFalse(metric.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenTypeIsNull() {
        Metric metric = Metric.builder()
            .name("cpu.usage")
            .value(75.5)
            .timestamp(Instant.now())
            .build();
        
        assertFalse(metric.isValid());
    }
    
    @Test
    void shouldBeInvalidWhenTimestampIsNull() {
        Metric metric = Metric.builder()
            .name("cpu.usage")
            .value(75.5)
            .type(MetricType.GAUGE)
            .build();
        
        assertFalse(metric.isValid());
    }
    
    @Test
    void shouldAcceptOptionalFields() {
        Map<String, String> labels = new HashMap<>();
        labels.put("instance", "server-01");
        labels.put("environment", "production");
        
        Metric metric = Metric.builder()
            .name("http.requests.total")
            .value(1000.0)
            .type(MetricType.COUNTER)
            .service("api-service")
            .host("server-01")
            .timestamp(Instant.now())
            .labels(labels)
            .build();
        
        assertTrue(metric.isValid());
        assertEquals("api-service", metric.getService());
        assertEquals("server-01", metric.getHost());
        assertEquals(labels, metric.getLabels());
    }
}

