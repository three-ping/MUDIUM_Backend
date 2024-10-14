package com.threeping.mudium.musical.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.dto.MusicalTotalDTO;
import com.threeping.mudium.musical.repository.MusicalRepository;
import com.threeping.mudium.performance.dto.PerformanceDTO;
import com.threeping.mudium.performance.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MusicalServiceImpl implements MusicalService {

    private final MusicalRepository musicalRepository;
    private final PerformanceService performanceService;

    @Autowired
    public MusicalServiceImpl(MusicalRepository musicalRepository,
                              PerformanceService performanceService) {
        this.musicalRepository = musicalRepository;
        this.performanceService = performanceService;
    }

    @Override
    @Transactional
    public MusicalTotalDTO findMusicalDetail(Long musicalId) {
        MusicalTotalDTO totalDTO = new MusicalTotalDTO();
        Musical musical = musicalRepository.findMusicalByMusicalId(musicalId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_ID));
        List<PerformanceDTO> performanceList = performanceService.findPerformances(musical);
        totalDTO.setTitle(musical.getTitle());
        totalDTO.setRating(musical.getRating());
        totalDTO.setPoster(musical.getPoster());
        totalDTO.setPerformanceList(performanceList);

        return totalDTO;
    }

    @Override
    @Transactional
    public Musical findMusicalByMusicalId(Long musicalId) {
        Musical musical = musicalRepository.findMusicalByMusicalId(musicalId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_ID));
        return musical;
    }
}
