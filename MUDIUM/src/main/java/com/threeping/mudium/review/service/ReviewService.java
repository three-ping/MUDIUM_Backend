package com.threeping.mudium.review.service;

import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;

public interface ReviewService {
    // 뮤지컬 ID를 통한 리뷰 조회
    ReviewRequestDTO findById(Long musicalId);
}
