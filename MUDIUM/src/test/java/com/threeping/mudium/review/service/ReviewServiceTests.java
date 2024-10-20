package com.threeping.mudium.review.service;

import com.threeping.mudium.review.dto.ReviewRequestDTO;
import com.threeping.mudium.review.dto.ReviewResponseDTO;
import com.threeping.mudium.review.dto.ReviewWithScopeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTests {

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceTests.class);
    @Autowired
    ReviewService reviewService;

    @DisplayName("리뷰 전체 조회한다.")
    @Test
    void findReviewByMusicalIdAndActiveStatusTest() {
        // Given
        Long musicalId = 1L;

        // When
        List<ReviewWithScopeDTO> dtoList = reviewService.findReviewsWithRatingsByMusicalId(musicalId);

        for (ReviewWithScopeDTO dto : dtoList) {
            log.info("별점과 함께 넘어온 리뷰" + dto.toString());
        }

        // Then
        assertNotNull(dtoList);   // 반환된 리스트가 null이 아닌지 확인
        assertFalse(dtoList.isEmpty());   // 적어도 하나의 리뷰가 존재하는지 확인

        // 추가적인 검증
        ReviewWithScopeDTO dto = dtoList.get(0);
        assertEquals(musicalId, dto.getMusicalId());    // 뮤지컬 ID가 일치하는지 확인
    }

    @DisplayName("리뷰 상세 조회한다.")
    @Test
    void findReviewByMusicalIdAndReviewIdAndActiveStatusTest() {
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

    @DisplayName("userId를 통한 리뷰 조회를 한다.")
    @Test
    void findReviewByUser_UserIdAndActiveStatusTest() {
        // Given
        Long userId = 1L;

        // When
        List<ReviewWithScopeDTO> reviewWithScopeDTO = reviewService.findReviewByUserId(userId);

        // Then
        assertNotNull(reviewWithScopeDTO);
        assertFalse(reviewWithScopeDTO.isEmpty());

        ReviewWithScopeDTO review = reviewWithScopeDTO.get(0);
        assertEquals(userId, review.getUserId());
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

    @DisplayName("리뷰 삭제를 한다.")
    @Test
    void deleteReviewTest() {
        // Given
        Long musicalId = 1L;
        Long reviewId = 23L;
        Long userId = 3L;

        // When
        assertDoesNotThrow(() -> reviewService.deleteReview(musicalId, reviewId, userId));
    }
}
