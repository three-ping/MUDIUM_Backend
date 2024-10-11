package com.threeping.mudium.review.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public ReviewRequestDTO findById(Long musicalId) {

        Review review = reviewRepository.findById(musicalId).orElseThrow(()->new CommonException(ErrorCode.INVALID_BOARD_ID));
        ReviewResponseDTO reviewResponseDTO = modelMapper.map(review, ReviewResponseDTO.class);

        return null;
    }
}
