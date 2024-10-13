package com.threeping.mudium.review.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewResponseDTO> findReviewByMusicalId(Long musicalId) {

        List<Review> reviews = reviewRepository.findReviewByMusicalId(musicalId);

        /* My. Stream API 사용 */
        List<ReviewResponseDTO> reviewResponseDTO = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
                .collect(Collectors.toList());

        return reviewResponseDTO;
    }
}
