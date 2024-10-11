package com.threeping.mudium.review.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all/{musicalId}")
    private ResponseDTO<?> findReviewByMusicalId(@PathVariable Long musicalId) {
        List<ReviewResponseDTO> reviewResponseDTO = reviewService.findReviewByMusicalId(musicalId);

        return ResponseDTO.ok(reviewResponseDTO);
    }
}
