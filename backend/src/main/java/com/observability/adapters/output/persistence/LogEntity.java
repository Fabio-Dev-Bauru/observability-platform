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

/**
 * JPA entity for log entries.
 * 
 * This adapter entity maps domain LogEntry to database table.
 */
@Entity
@Table(name = "logs", indexes = {
    @Index(name = "idx_logs_timestamp", columnList = "timestamp"),
    @Index(name = "idx_logs_level", columnList = "level"),
    @Index(name = "idx_logs_service", columnList = "service")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false, length = 50)
    private String level;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(nullable = false)
    private String service;
    
    private String host;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> tags;
    
    @Column(name = "trace_id")
    private String traceId;
    
    @Column(name = "span_id")
    private String spanId;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;
    
    @Column(name = "created_at")
    private Instant createdAt;
}

