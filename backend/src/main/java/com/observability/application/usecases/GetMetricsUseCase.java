package com.observability.application.usecases;

import com.observability.domain.entities.Metric;
import com.observability.domain.ports.output.MetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMetricsUseCase {
    
    private final MetricRepository metricRepository;
    
    public List<Metric> execute(
        String name,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        log.debug("Retrieving metrics with filters - name: {}, service: {}, host: {}", name, service, host);
        
        if (startTime == null) {
            startTime = Instant.now().minusSeconds(3600);
        }
        if (endTime == null) {
            endTime = Instant.now();
        }
        
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20;
        }
        
        return metricRepository.findByFilters(name, service, host, startTime, endTime, page, size);
    }
}

