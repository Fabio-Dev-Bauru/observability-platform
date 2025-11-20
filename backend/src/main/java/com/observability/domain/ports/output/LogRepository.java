package com.observability.domain.ports.output;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.valueobjects.LogLevel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for log persistence operations.
 * 
 * This interface defines the contract for storing and retrieving
 * log entries, keeping the domain layer decoupled from infrastructure.
 */
public interface LogRepository {
    
    /**
     * Saves a log entry.
     * 
     * @param logEntry The log entry to save
     * @return The saved log entry with generated ID
     */
    LogEntry save(LogEntry logEntry);
    
    /**
     * Finds a log entry by ID.
     * 
     * @param id The log entry ID
     * @return Optional containing the log entry if found
     */
    Optional<LogEntry> findById(String id);
    
    /**
     * Finds log entries with filters.
     * 
     * @param level Optional log level filter
     * @param service Optional service filter
     * @param host Optional host filter
     * @param startTime Start of time range
     * @param endTime End of time range
     * @param page Page number (0-indexed)
     * @param size Page size
     * @return List of log entries matching the filters
     */
    List<LogEntry> findByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    );
    
    /**
     * Counts log entries matching the filters.
     * 
     * @param level Optional log level filter
     * @param service Optional service filter
     * @param host Optional host filter
     * @param startTime Start of time range
     * @param endTime End of time range
     * @return Total count of matching log entries
     */
    long countByFilters(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime
    );
}

