package com.observability.adapters.output.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface MetricJpaRepository extends JpaRepository<MetricEntity, String> {
    
    @Query("SELECT m FROM MetricEntity m WHERE " +
           "(:name IS NULL OR m.name = :name) AND " +
           "(:service IS NULL OR m.service = :service) AND " +
           "(:host IS NULL OR m.host = :host) AND " +
           "m.timestamp >= :startTime AND " +
           "m.timestamp <= :endTime " +
           "ORDER BY m.timestamp DESC")
    Page<MetricEntity> findByFilters(
        @Param("name") String name,
        @Param("service") String service,
        @Param("host") String host,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime,
        Pageable pageable
    );
}

