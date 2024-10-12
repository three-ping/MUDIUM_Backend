package com.threeping.mudium.guidebook.service;

import com.threeping.mudium.guidebook.dto.request.RecommendedRequestDTO;
import com.threeping.mudium.guidebook.entity.RecommendedMusical;
import com.threeping.mudium.guidebook.repository.RecommendedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class RecommendedServiceImpl implements RecommendedService {

    private final RecommendedRepository recommendedRepository;

    @Autowired
    public RecommendedServiceImpl(RecommendedRepository recommendedRepository) {
        this.recommendedRepository = recommendedRepository;
    }

    @Override
    @Transactional
    public RecommendedMusical createRecommended(RecommendedRequestDTO recommendedRequestDTO) {
        RecommendedMusical recommendedMusical = RecommendedMusical.builder()
                .musicalTitle(recommendedRequestDTO.getMusicalTitle())
                .musicalDescription(recommendedRequestDTO.getMusicalDescription())
                .userId(recommendedRequestDTO.getUserId())
                .build();

        // 예외처리
       return recommendedRepository.save(recommendedMusical);
    }

    @Override
    @Transactional
    public void updateRecommended(RecommendedRequestDTO recommendedRequestDTO,Long recommendedId) {
        Optional<RecommendedMusical> optionalRecommendedMusical = recommendedRepository.findById(recommendedId);

        if (optionalRecommendedMusical.isPresent()) {
            RecommendedMusical recommendedMusical = optionalRecommendedMusical.get();
            recommendedMusical.setMusicalTitle(recommendedRequestDTO.getMusicalTitle());
            recommendedMusical.setMusicalDescription(recommendedRequestDTO.getMusicalDescription());

             recommendedRepository.save(recommendedMusical);
        } else {
            throw new EntityNotFoundException("수정할 추천작이 없습니다!");
        }
    }

    //    전체 목록 조회
    @Transactional
    @Override
    public List<RecommendedRequestDTO> findRecommendedList() {

        List<RecommendedMusical> all = recommendedRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("추천 작품이 없습니다.");
            return Collections.emptyList(); // 빈 리스트 반환
        }

        List<RecommendedRequestDTO> recommendedRequestDTOList = new ArrayList<>();

        for (RecommendedMusical recommendedMusical : all) {
            RecommendedRequestDTO recommendedRequestDTO = RecommendedRequestDTO.builder()
                    .musicalTitle(recommendedMusical.getMusicalTitle())
                    .musicalDescription(recommendedMusical.getMusicalDescription())
                    .userId ( recommendedMusical.getUserId() )
                    .build();

            recommendedRequestDTOList.add(recommendedRequestDTO);
        }

        return recommendedRequestDTOList;
    }

    //    추천 작품 조회하기
    @Override
    @Transactional
    public RecommendedRequestDTO findByRecommendedId(Long recommendedId) {
        Optional<RecommendedMusical> boardWrapper = recommendedRepository.findById(recommendedId);
        RecommendedMusical recommendedMusical = boardWrapper.get();

        return RecommendedRequestDTO.builder()
                .musicalTitle(recommendedMusical.getMusicalTitle())
                .musicalDescription(recommendedMusical.getMusicalDescription())
                .build();
    }
}
