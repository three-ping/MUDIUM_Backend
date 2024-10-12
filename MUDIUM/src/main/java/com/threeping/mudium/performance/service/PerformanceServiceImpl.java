package com.threeping.mudium.performance.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.performance.aggregate.Performance;
import com.threeping.mudium.performance.dto.PerformanceDTO;
import com.threeping.mudium.performance.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceServiceImpl(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Transactional
    @Override
    public List<PerformanceDTO> findPerformances(Musical musical) {
        List<Performance> performanceList = performanceRepository.findAllByMusical(musical);
        if (performanceList.isEmpty()) {
            throw new CommonException(ErrorCode.INVALID_MUSICAL_ID);
        }
        List<PerformanceDTO> dtoList = performanceList.stream()
                .map(performance -> {
                    PerformanceDTO dto = new PerformanceDTO();
                    dto.setRegion(performance.getRegion());
                    dto.setTheater(performance.getTheater());
                    dto.setEndDate(performance.getEndDate());
                    dto.setStartDate(performance.getStartDate());
                    return dto;
                }).collect(Collectors.toList());
        return dtoList;
    }
}
