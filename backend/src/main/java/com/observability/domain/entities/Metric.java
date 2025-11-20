package com.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a metric in the system.
 * 
 * Metrics are time-series data points that represent
 * measurements of system behavior or performance.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    
    private String id;
    private String name;
    private Double value;
    private String service;
    private String host;
    private Instant timestamp;
    private Map<String, String> labels;
    private MetricType type;
    
    /**
     * Validates if the metric has all required fields.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.isBlank()
            && value != null
            && timestamp != null
            && type != null;
    }
}

