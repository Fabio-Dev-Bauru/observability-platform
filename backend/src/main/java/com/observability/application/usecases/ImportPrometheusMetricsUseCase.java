package com.observability.application.usecases;

import com.observability.domain.entities.Metric;
import com.observability.domain.ports.output.EventPublisher;
import com.observability.domain.ports.output.MetricRepository;
import com.observability.infrastructure.prometheus.PrometheusParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportPrometheusMetricsUseCase {
    
    private final PrometheusParser prometheusParser;
    private final MetricRepository metricRepository;
    private final EventPublisher eventPublisher;
    
    public List<Metric> execute(String prometheusData) {
        log.debug("Importing Prometheus metrics");
        
        List<Metric> metrics = prometheusParser.parsePrometheusFormat(prometheusData);
        
        for (Metric metric : metrics) {
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
        }
        
        log.info("Imported {} metrics from Prometheus", metrics.size());
        return metrics;
    }
}

