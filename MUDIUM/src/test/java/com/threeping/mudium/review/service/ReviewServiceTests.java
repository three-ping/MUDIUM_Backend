package com.threeping.mudium.review.service;

import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.Review;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("리뷰 전체 조회한다.")
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

    @DisplayName("리뷰 상세 조회한다.")
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

    @DisplayName("리뷰 작성을 한다.")
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

    @DisplayName("리뷰 수정을 한다.")
    @Test
    void updateReviewTest() {
        // Given
        Long musicalId = 1L;
        Long reviewId = 11L;
        Long userId = 5L;
        String content = "지루하지 않고 몰입하며 봤습니다. 재미있어요!!!";

        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO(content, userId);

        // When
        assertDoesNotThrow(() -> reviewService.updateReview(musicalId, reviewId, reviewRequestDTO));
    }
}
