package com.observability.adapters.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "alerts", indexes = {
    @Index(name = "idx_alerts_status", columnList = "status"),
    @Index(name = "idx_alerts_severity", columnList = "severity"),
    @Index(name = "idx_alerts_service", columnList = "service"),
    @Index(name = "idx_alerts_triggered_at", columnList = "triggered_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, length = 50)
    private String severity;
    
    @Column(nullable = false, length = 50)
    private String status;
    
    private String service;
    
    @Column(name = "triggered_at", nullable = false)
    private Instant triggeredAt;
    
    @Column(name = "resolved_at")
    private Instant resolvedAt;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;
    
    @Column(name = "rule_id")
    private String ruleId;
    
    @Column(name = "created_at")
    private Instant createdAt;
}

