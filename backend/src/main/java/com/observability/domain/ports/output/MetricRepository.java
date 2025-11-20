package com.observability.domain.ports.output;

import com.observability.domain.entities.Metric;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for metric persistence operations.
 */
public interface MetricRepository {
    
    /**
     * Saves a metric.
     * 
     * @param metric The metric to save
     * @return The saved metric with generated ID
     */
    Metric save(Metric metric);
    
    /**
     * Finds a metric by ID.
     * 
     * @param id The metric ID
     * @return Optional containing the metric if found
     */
    Optional<Metric> findById(String id);
    
    /**
     * Finds metrics with filters.
     * 
     * @param name Optional metric name filter
     * @param service Optional service filter
     * @param host Optional host filter
     * @param startTime Start of time range
     * @param endTime End of time range
     * @param page Page number (0-indexed)
     * @param size Page size
     * @return List of metrics matching the filters
     */
    List<Metric> findByFilters(
        String name,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    );
}

