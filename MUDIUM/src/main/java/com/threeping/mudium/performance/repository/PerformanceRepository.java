package com.threeping.mudium.performance.repository;

import com.threeping.mudium.performance.aggregate.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Optional<Performance> findByMusicalId(long musicalId);
    Optional<Performance> findPerformanceByMusicalIdAndRegion(Long musicalId, String region);
}
