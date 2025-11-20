package com.observability.domain.ports.output;

import com.observability.domain.entities.Metric;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MetricRepository {
    
    Metric save(Metric metric);
    
    Optional<Metric> findById(String id);
    
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

