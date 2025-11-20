package com.observability.adapters.output.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

/**
 * Spring Data JPA repository for log entities.
 */
@Repository
public interface LogJpaRepository extends JpaRepository<LogEntity, String> {
    
    @Query("SELECT l FROM LogEntity l WHERE " +
           "(:level IS NULL OR l.level = :level) AND " +
           "(:service IS NULL OR l.service = :service) AND " +
           "(:host IS NULL OR l.host = :host) AND " +
           "l.timestamp >= :startTime AND " +
           "l.timestamp <= :endTime " +
           "ORDER BY l.timestamp DESC")
    Page<LogEntity> findByFilters(
        @Param("level") String level,
        @Param("service") String service,
        @Param("host") String host,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime,
        Pageable pageable
    );
    
    @Query("SELECT COUNT(l) FROM LogEntity l WHERE " +
           "(:level IS NULL OR l.level = :level) AND " +
           "(:service IS NULL OR l.service = :service) AND " +
           "(:host IS NULL OR l.host = :host) AND " +
           "l.timestamp >= :startTime AND " +
           "l.timestamp <= :endTime")
    long countByFilters(
        @Param("level") String level,
        @Param("service") String service,
        @Param("host") String host,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime
    );
}

