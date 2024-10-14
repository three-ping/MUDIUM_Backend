package com.threeping.mudium.secretreview.service;

import com.threeping.mudium.secretreview.dto.SecretReviewResponseDTO;

import java.util.List;

public interface SecretReviewService{
    // 비밀리뷰 전체 조회
    List<SecretReviewResponseDTO> findSecretReviewByUserId(Long userId);

    // 비밀리뷰 상세 조회
    List<SecretReviewResponseDTO> findSecretReviewByUserIdAndSecretReviewId(Long userId, Long secretReviewId);
}
