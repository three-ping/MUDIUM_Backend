package com.threeping.mudium.performance.service;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.performance.dto.PerformanceDTO;

import java.util.List;
import java.util.Optional;

public interface PerformanceService {
    List<PerformanceDTO> findPerformances(Long musicalId);

    PerformanceDTO findPerformanceByMusicalIdAndRegion(Long musicalId, String region);
}
