package com.threeping.mudium.reviewcomment.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.reviewcomment.dto.ReviewCommentDTO;
import com.threeping.mudium.reviewcomment.service.ReviewCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/review-comment")
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @Autowired
    public ReviewCommentController(ReviewCommentService reviewCommentService) {
        this.reviewCommentService = reviewCommentService;
    }

    @GetMapping("/{reviewId}")
    public ResponseDTO<?> findReviewComment(@PathVariable Long reviewId) {
        List<ReviewCommentDTO> dtoList = reviewCommentService.findAllComment(reviewId);

        ResponseDTO<List<ReviewCommentDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(dtoList);
        responseDTO.setSuccess(true);
        responseDTO.setHttpStatus(HttpStatus.OK);

        return responseDTO;
    }
}
