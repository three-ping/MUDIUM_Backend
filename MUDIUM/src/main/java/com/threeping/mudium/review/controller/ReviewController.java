package com.threeping.mudium.review.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 전체 조회
    @GetMapping("/{musicalId}")
    private ResponseDTO<?> findReviewByMusicalId(@PathVariable Long musicalId) {
        List<ReviewResponseDTO> reviewResponseDTO = reviewService.findReviewByMusicalId(musicalId);

        return ResponseDTO.ok(reviewResponseDTO);
    }

    // 리뷰 상세 조회
    @GetMapping("/{musicalId}/{reviewId}")
    private ResponseDTO<?> findReviewByMusicalIdAndReviewId(@PathVariable Long musicalId,
                                                            @PathVariable Long reviewId) {
        List<ReviewResponseDTO> reviewResponseDTO = reviewService.findReviewByMusicalIdAndReviewId(musicalId, reviewId);

        return ResponseDTO.ok(reviewResponseDTO);
    }

    // 리뷰 작성
    @PostMapping("/{musicalId}")
    private ResponseDTO<?> createReview(@PathVariable Long musicalId,
                                        @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.createReview(musicalId, reviewRequestDTO);

        return ResponseDTO.ok("리뷰 등록 성공!!!");
    }
}
