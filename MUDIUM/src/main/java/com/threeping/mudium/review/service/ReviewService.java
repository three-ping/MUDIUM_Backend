package com.threeping.mudium.review.service;

import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    // 뮤지컬 ID를 통한 리뷰 조회
    List<ReviewResponseDTO> findReviewByMusicalId(Long musicalId);
}
