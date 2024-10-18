package com.threeping.mudium.reviewcomment.service;

import com.threeping.mudium.reviewcomment.dto.ReviewCommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReviewCommentServiceImplTests {

    private ReviewCommentService reviewCommentService;

    @Autowired
    public void setReviewCommentService(ReviewCommentService reviewCommentService) {
        this.reviewCommentService = reviewCommentService;
    }

    @DisplayName("리뷰에 달린 모든 댓글 조회")
    @Test
    void getReviewComments() {

        Long reviewId = 1L;

        List<ReviewCommentDTO> commentDTOList = reviewCommentService.findAllComment(reviewId);
        for (ReviewCommentDTO commentDTO : commentDTOList) {
            log.info(commentDTO.toString());
        }

        assertNotNull(commentDTOList);
        assertTrue(commentDTOList.size() > 0);
        assertTrue(commentDTOList.get(0).getCommentId().equals(reviewId));
    }
}