package com.observability.adapters.output.persistence;

import com.observability.domain.entities.Metric;
import com.observability.domain.ports.output.MetricRepository;
import com.observability.domain.valueobjects.MetricType;
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
public class MetricRepositoryAdapter implements MetricRepository {
    
    private final MetricJpaRepository jpaRepository;
    
    @Override
    public Metric save(Metric metric) {
        MetricEntity entity = toEntity(metric);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(Instant.now());
        }
        MetricEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Metric> findById(String id) {
        return jpaRepository.findById(id)
            .map(this::toDomain);
    }
    
    @Override
    public List<Metric> findByFilters(
        String name,
        String service,
        String host,
        Instant startTime,
        Instant endTime,
        int page,
        int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        
        return jpaRepository.findByFilters(name, service, host, startTime, endTime, pageable)
            .getContent()
            .stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    private MetricEntity toEntity(Metric metric) {
        return MetricEntity.builder()
            .id(metric.getId())
            .name(metric.getName())
            .value(metric.getValue())
            .service(metric.getService())
            .host(metric.getHost())
            .timestamp(metric.getTimestamp())
            .labels(metric.getLabels())
            .type(metric.getType().name())
            .build();
    }
    
    private Metric toDomain(MetricEntity entity) {
        return Metric.builder()
            .id(entity.getId())
            .name(entity.getName())
            .value(entity.getValue())
            .service(entity.getService())
            .host(entity.getHost())
            .timestamp(entity.getTimestamp())
            .labels(entity.getLabels())
            .type(MetricType.valueOf(entity.getType()))
            .build();
    }
}

