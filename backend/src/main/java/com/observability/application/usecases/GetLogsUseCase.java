package com.observability.application.usecases;

import com.observability.domain.entities.LogEntry;
import com.observability.domain.ports.output.LogRepository;
import com.observability.domain.valueobjects.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Use case for retrieving log entries with filters.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetLogsUseCase {
    
    private final LogRepository logRepository;
    
    /**
     * Retrieves log entries with optional filters.
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
    public List<LogEntry> execute(
        LogLevel level,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        log.debug("Retrieving logs with filters - level: {}, service: {}, host: {}", level, service, host);
        
        // Set default time range if not provided
        if (startTime == null) {
            startTime = Instant.now().minusSeconds(3600); // Last hour
        }
        if (endTime == null) {
            endTime = Instant.now();
        }
        
        // Set default pagination
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20; // Default page size
        }
        
        return logRepository.findByFilters(level, service, host, startTime, endTime, page, size);
    }
}

