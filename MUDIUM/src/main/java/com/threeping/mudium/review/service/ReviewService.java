package com.threeping.mudium.review.service;

import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    // 리뷰 전체 조회
    List<ReviewResponseDTO> findReviewByMusicalId(Long musicalId);

    // 리뷰 상세 조회
    List<ReviewResponseDTO> findReviewByMusicalIdAndReviewId(Long musicalId, Long reviewId);

    // 리뷰 작성
    void createReview(Long musicalId, ReviewRequestDTO reviewRequestDTO);

    // 리뷰 수정
    void updateReview(Long musicalId, Long reviewId, ReviewRequestDTO reviewRequestDTO);
}
