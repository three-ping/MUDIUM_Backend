package com.threeping.mudium.secretreview.service;

import com.threeping.mudium.secretreview.dto.SecretReviewResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SecretReviewServiceTests {

    @Autowired
    SecretReviewService secretReviewService;

    @DisplayName("비밀리뷰 전체 조회한다.")
    @Test
    void findSecretReviewByUserIdAndActiveStatusTest() {
        // Given
        Long userId = 1L;

        // When
        List<SecretReviewResponseDTO> secretReviewResponseDTO =
                secretReviewService.findSecretReviewByUserId(userId);

        // Then
        assertNotNull(secretReviewResponseDTO);
        assertFalse(secretReviewResponseDTO.isEmpty());

        SecretReviewResponseDTO secretReview = secretReviewResponseDTO.get(0);
        assertEquals(userId, secretReview.getUserId());
    }

    @DisplayName("비밀리뷰 상세 조회한다.")
    @Test
    void findSecretReviewByUserIdAndSecretReviewIdAndActiveStatusTest() {
        // Given
        Long userId = 1L;
        Long secretReviewId = 1L;

        // When
        List<SecretReviewResponseDTO> secretReviewResponseDTO =
                secretReviewService.findSecretReviewByUserIdAndSecretReviewId(userId, secretReviewId);

        // Then
        assertNotNull(secretReviewResponseDTO);
        assertFalse(secretReviewResponseDTO.isEmpty());

        SecretReviewResponseDTO secretReview = secretReviewResponseDTO.get(0);
        assertEquals(userId, secretReview.getUserId());
        assertEquals(secretReviewId, secretReview.getSecretReviewId());
    }

}