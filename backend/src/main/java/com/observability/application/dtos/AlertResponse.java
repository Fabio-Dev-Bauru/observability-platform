package com.observability.application.dtos;

import com.observability.domain.valueobjects.AlertSeverity;
import com.observability.domain.valueobjects.AlertStatus;
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
public class AlertResponse {
    
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
}

