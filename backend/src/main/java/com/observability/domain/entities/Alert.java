package com.observability.domain.entities;

import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

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
    
    public void resolve() {
        this.status = AlertStatus.RESOLVED;
        this.resolvedAt = Instant.now();
    }
    
    public void activate() {
        this.status = AlertStatus.ACTIVE;
        this.triggeredAt = Instant.now();
    }
    
    public boolean isValid() {
        return name != null && !name.isBlank()
            && severity != null
            && status != null
            && triggeredAt != null;
    }
}

