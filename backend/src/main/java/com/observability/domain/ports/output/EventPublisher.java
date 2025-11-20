package com.observability.domain.ports.output;

import com.observability.domain.entities.Alert;
import com.observability.domain.entities.LogEntry;
import com.observability.domain.entities.Metric;

/**
 * Port interface for publishing domain events.
 * 
 * This interface decouples the domain from the messaging infrastructure,
 * allowing events to be published to Kafka, RabbitMQ, or other systems.
 */
public interface EventPublisher {
    
    /**
     * Publishes a log entry event.
     * 
     * @param logEntry The log entry to publish
     */
    void publishLogEvent(LogEntry logEntry);
    
    /**
     * Publishes a metric event.
     * 
     * @param metric The metric to publish
     */
    void publishMetricEvent(Metric metric);
    
    /**
     * Publishes an alert event.
     * 
     * @param alert The alert to publish
     */
    void publishAlertEvent(Alert alert);
}

