package com.threeping.mudium.review.service;

import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class ReviewServiceTests {

    @Autowired
    ReviewService reviewService;

    // 리뷰 전체 조회
    @Test
    void findReviewByMusicalIdTest() {
        // Given
        Long musicalId = 1L;

        // When
        List<ReviewResponseDTO> reviewResponseDTO = reviewService.findReviewByMusicalId(musicalId);

        // Then
        assertNotNull(reviewResponseDTO);   // 반환된 리스트가 null이 아닌지 확인
        assertFalse(reviewResponseDTO.isEmpty());   // 적어도 하나의 리뷰가 존재하는지 확인

        // 추가적인 검증
        ReviewResponseDTO firstReview = reviewResponseDTO.get(0);
        assertEquals(musicalId, firstReview.getMusicalId());    // 뮤지컬 ID가 일치하는지 확인
    }

    // 리뷰 상세 조회
    @Test
    void findReviewByMusicalIdAndReviewIdTest() {
        // Given
        Long musicalId = 1L;
        Long reviewId = 11L;

        // When
        List<ReviewResponseDTO> reviewResponseDTO = reviewService.findReviewByMusicalIdAndReviewId(musicalId, reviewId);

        // Then
        assertNotNull(reviewResponseDTO);
        assertFalse(reviewResponseDTO.isEmpty());

        ReviewResponseDTO review = reviewResponseDTO.get(0);
        assertEquals(musicalId, review.getMusicalId());
        assertEquals(reviewId, review.getReviewId());
    }

    // 리뷰 작성
    @Test
    void createReviewTest() {
        // Given
        Long musicalId = 2L;
        Long userId = 1L;
        String content = "정말 판타스틱한 뮤지컬이에요!";

        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO(content, userId);

        // When
        assertDoesNotThrow(() -> reviewService.createReview(musicalId, reviewRequestDTO));


    }
}
