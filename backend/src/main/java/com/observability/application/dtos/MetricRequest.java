package com.observability.application.dtos;

import com.observability.domain.valueobjects.MetricType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MetricRequest {
    
    @NotBlank(message = "Metric name is required")
    private String name;
    
    @NotNull(message = "Metric value is required")
    private Double value;
    
    private String service;
    
    private String host;
    
    private Instant timestamp;
    
    private Map<String, String> labels;
    
    @NotNull(message = "Metric type is required")
    private MetricType type;
}

