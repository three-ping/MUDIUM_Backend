package com.threeping.mudium.review.service;

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

    @Test
    void findReviewByMusicalId() {
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
}