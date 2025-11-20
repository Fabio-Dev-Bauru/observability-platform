package com.observability.domain.ports.output;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.valueobjects.LogLevel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LogRepository {
    
    LogEntry save(LogEntry logEntry);
    
    Optional<LogEntry> findById(String id);
    
    List<LogEntry> findByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    );
    
    long countByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime
    );
}

