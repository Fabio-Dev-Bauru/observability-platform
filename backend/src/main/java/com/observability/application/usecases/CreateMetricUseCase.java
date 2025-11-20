package com.observability.application.usecases;

import com.observability.domain.entities.Metric;
import com.observability.domain.exceptions.InvalidMetricException;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.ports.output.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMetricUseCase {
    
    private final MetricRepository metricRepository;
    private final EventPublisher eventPublisher;
    
    public Metric execute(Metric metric) {
        log.debug("Creating metric: {}", metric.getName());
        
        if (!metric.isValid()) {
            throw new InvalidMetricException("Metric must contain name, value, timestamp, and type");
        }
        
        if (metric.getId() == null || metric.getId().isBlank()) {
            metric = Metric.builder()
                .id(UUID.randomUUID().toString())
                .name(metric.getName())
                .value(metric.getValue())
                .service(metric.getService())
                .host(metric.getHost())
                .timestamp(metric.getTimestamp())
                .labels(metric.getLabels())
                .type(metric.getType())
                .build();
        }
        
        Metric savedMetric = metricRepository.save(metric);
        
        eventPublisher.publishMetricEvent(savedMetric);
        
        log.debug("Metric created with ID: {}", savedMetric.getId());
        return savedMetric;
    }
}

