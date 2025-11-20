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
@Table(name = "metrics", indexes = {
    @Index(name = "idx_metrics_timestamp", columnList = "timestamp"),
    @Index(name = "idx_metrics_name", columnList = "name"),
    @Index(name = "idx_metrics_service", columnList = "service")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double value;
    
    private String service;
    
    private String host;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> labels;
    
    @Column(nullable = false, length = 50)
    private String type;
    
    @Column(name = "created_at")
    private Instant createdAt;
}

