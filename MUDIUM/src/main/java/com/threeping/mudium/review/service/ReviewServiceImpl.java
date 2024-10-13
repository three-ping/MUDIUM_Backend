package com.threeping.mudium.review.service;

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

    // 리뷰 전체 조회
    @Override
    public List<ReviewResponseDTO> findReviewByMusicalId(Long musicalId) {

        /* 필기. 엔티티에 @ManyToOne이 있어서 JPA를 해당 메소드로 가능 */
        List<Review> reviews = reviewRepository.findAllByMusical_MusicalId(musicalId);

        /* 필기. Stream API 사용 */
        List<ReviewResponseDTO> reviewResponseDTO = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
                .collect(Collectors.toList());

        return reviewResponseDTO;
    }

    // 리뷰 상세 조회
    @Override
    public List<ReviewResponseDTO> findReviewByMusicalIdAndReviewId(Long musicalId, Long reviewId) {

        List<Review> reviews = reviewRepository.findByMusical_MusicalIdAndReviewId(musicalId, reviewId);

        List<ReviewResponseDTO> reviewResponseDTO = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
                .collect(Collectors.toList());

        return reviewResponseDTO;
    }
}
