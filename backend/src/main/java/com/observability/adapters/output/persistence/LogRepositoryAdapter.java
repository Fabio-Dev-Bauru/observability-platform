package com.observability.adapters.output.persistence;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.valueobjects.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LogRepositoryAdapter implements LogRepository {
    
    private final LogJpaRepository jpaRepository;
    
    @Override
    public LogEntry save(LogEntry logEntry) {
        LogEntity entity = toEntity(logEntry);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
        }
        LogEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<LogEntry> findById(String id) {
        return jpaRepository.findById(id)
            .map(this::toDomain);
    }
    
    @Override
    public List<LogEntry> findByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        String levelStr = level != null ? level.name() : null;
        
        return jpaRepository.findByFilters(levelStr, service, host, startTime, endTime, pageable)
            .getContent()
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public long countByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime
    ) {
        String levelStr = level != null ? level.name() : null;
        return jpaRepository.countByFilters(levelStr, service, host, startTime, endTime);
    }
    
    private LogEntity toEntity(LogEntry logEntry) {
        return LogEntity.builder()
            .id(logEntry.getId())
            .level(logEntry.getLevel().name())
            .message(logEntry.getMessage())
            .service(logEntry.getService())
            .host(logEntry.getHost())
            .timestamp(logEntry.getTimestamp())
            .tags(logEntry.getTags())
            .traceId(logEntry.getTraceId())
            .spanId(logEntry.getSpanId())
            .metadata(logEntry.getMetadata())
            .build();
    }
    
    private LogEntry toDomain(LogEntity entity) {
        return LogEntry.builder()
            .id(entity.getId())
            .level(LogLevel.valueOf(entity.getLevel()))
            .message(entity.getMessage())
            .service(entity.getService())
            .host(entity.getHost())
            .timestamp(entity.getTimestamp())
            .tags(entity.getTags())
            .traceId(entity.getTraceId())
            .spanId(entity.getSpanId())
            .metadata(entity.getMetadata())
            .build();
    }
}

