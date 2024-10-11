package com.threeping.mudium.performance.service;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.performance.dto.PerformanceDTO;

import java.util.List;

public interface PerformanceService {
    List<PerformanceDTO> findPerformances(Musical musical);
}
