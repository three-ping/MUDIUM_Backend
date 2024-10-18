package com.threeping.mudium.performance.repository;

import com.threeping.mudium.performance.aggregate.PerformanceRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRankRepository extends JpaRepository<PerformanceRank, Long> {
}
