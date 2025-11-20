package com.observability.adapters.output.messaging;

import com.observability.domain.entities.Alert;
import com.observability.domain.entities.LogEntry;
import com.observability.domain.entities.Metric;
import com.observability.domain.ports.output.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka implementation of EventPublisher.
 * 
 * Publishes domain events to Kafka topics for real-time processing.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String LOGS_TOPIC = "logs";
    private static final String METRICS_TOPIC = "metrics";
    private static final String ALERTS_TOPIC = "alerts";
    
    @Override
    public void publishLogEvent(LogEntry logEntry) {
        try {
            String message = objectMapper.writeValueAsString(logEntry);
            kafkaTemplate.send(LOGS_TOPIC, logEntry.getId(), message);
            log.debug("Published log event to topic: {}", LOGS_TOPIC);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize log entry: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public void publishMetricEvent(Metric metric) {
        try {
            String message = objectMapper.writeValueAsString(metric);
            kafkaTemplate.send(METRICS_TOPIC, metric.getId(), message);
            log.debug("Published metric event to topic: {}", METRICS_TOPIC);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize metric: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public void publishAlertEvent(Alert alert) {
        try {
            String message = objectMapper.writeValueAsString(alert);
            kafkaTemplate.send(ALERTS_TOPIC, alert.getId(), message);
            log.debug("Published alert event to topic: {}", ALERTS_TOPIC);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize alert: {}", e.getMessage(), e);
        }
    }
}

