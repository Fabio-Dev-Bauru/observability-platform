package com.observability.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrometheusMetric {
    
    private String name;
    private Double value;
    private Map<String, String> labels;
    private Long timestamp;
}

