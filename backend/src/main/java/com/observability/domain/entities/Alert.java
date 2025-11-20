package com.observability.domain.entities;

import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Represents an alert in the system.
 * 
 * Alerts are triggered when certain conditions are met,
 * such as threshold violations or pattern detection.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    
    private String id;
    private String name;
    private String description;
    private AlertSeverity severity;
    private AlertStatus status;
    private String service;
    private Instant triggeredAt;
    private Instant resolvedAt;
    private Map<String, Object> metadata;
    private String ruleId;
    
    /**
     * Marks the alert as resolved.
     */
    public void resolve() {
        this.status = AlertStatus.RESOLVED;
        this.resolvedAt = Instant.now();
    }
    
    /**
     * Marks the alert as active.
     */
    public void activate() {
        this.status = AlertStatus.ACTIVE;
        this.triggeredAt = Instant.now();
    }
    
    /**
     * Validates if the alert has all required fields.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.isBlank()
            && severity != null
            && status != null
            && triggeredAt != null;
    }
}

