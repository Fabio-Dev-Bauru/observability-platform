package com.observability.adapters.output.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AlertJpaRepository extends JpaRepository<AlertEntity, String> {
    
    @Query("SELECT a FROM AlertEntity a WHERE " +
           "(:status IS NULL OR a.status = :status) AND " +
           "(:severity IS NULL OR a.severity = :severity) AND " +
           "(:service IS NULL OR a.service = :service) AND " +
           "a.triggeredAt >= :startTime AND " +
           "a.triggeredAt <= :endTime " +
           "ORDER BY a.triggeredAt DESC")
    Page<AlertEntity> findByFilters(
        @Param("status") String status,
        @Param("severity") String severity,
        @Param("service") String service,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime,
        Pageable pageable
    );
    
    List<AlertEntity> findByStatus(String status);
}

