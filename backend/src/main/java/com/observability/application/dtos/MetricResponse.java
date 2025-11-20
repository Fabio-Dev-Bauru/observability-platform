package com.observability.application.dtos;

import com.observability.domain.valueobjects.MetricType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricResponse {
    
    private String id;
    private String name;
    private Double value;
    private String service;
    private String host;
    private Instant timestamp;
    private Map<String, String> labels;
    private MetricType type;
}

