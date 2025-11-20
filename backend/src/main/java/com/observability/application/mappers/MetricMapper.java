package com.observability.application.mappers;

import com.observability.application.dtos.MetricRequest;
import com.observability.application.dtos.MetricResponse;
import com.observability.domain.entities.Metric;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MetricMapper {
    
    public Metric toEntity(MetricRequest request) {
        return Metric.builder()
            .name(request.getName())
            .value(request.getValue())
            .service(request.getService())
            .host(request.getHost())
            .timestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now())
            .labels(request.getLabels())
            .type(request.getType())
            .build();
    }
    
    public MetricResponse toResponse(Metric metric) {
        return MetricResponse.builder()
            .id(metric.getId())
            .name(metric.getName())
            .value(metric.getValue())
            .service(metric.getService())
            .host(metric.getHost())
            .timestamp(metric.getTimestamp())
            .labels(metric.getLabels())
            .type(metric.getType())
            .build();
    }
}

