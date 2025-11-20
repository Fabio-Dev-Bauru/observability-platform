package com.observability.domain.ports.output;

import com.observability.domain.entities.Alert;
import com.observability.domain.entities.LogEntry;
import com.observability.domain.entities.Metric;

public interface EventPublisher {
    
    void publishLogEvent(LogEntry logEntry);
    
    void publishMetricEvent(Metric metric);
    
    void publishAlertEvent(Alert alert);
}

