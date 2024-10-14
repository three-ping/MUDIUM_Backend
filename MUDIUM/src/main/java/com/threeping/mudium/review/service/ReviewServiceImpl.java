package com.threeping.mudium.review.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.repository.MusicalRepository;
import com.threeping.mudium.review.aggregate.dto.ReviewRequestDTO;
import com.threeping.mudium.review.aggregate.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.ActiveStatus;
import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.review.repository.ReviewRepository;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import com.threeping.mudium.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MusicalRepository musicalRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper, UserRepository userRepository, MusicalRepository musicalRepository) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.musicalRepository = musicalRepository;
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

    // 리뷰 작성
    @Override
    public void createReview(Long musicalId, ReviewRequestDTO reviewRequestDTO) {

        List<Review> reviews = reviewRepository.findAllByMusical_MusicalId(musicalId);

        // 뮤지컬 중에서 userId가 없거나 만약 있으면 활성화 상태가 비활성화이면 리뷰 작성 / userId가 있고 활성화 상태가 활성화면 리뷰 작성 못하게.
        boolean hasActiveReview = reviews.stream()
                .anyMatch(review -> review.getUser().getUserId().equals(
                        reviewRequestDTO.getUserId()) && review.getActiveStatus() == ActiveStatus.ACTIVE);

        if (hasActiveReview) {
            throw new CommonException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        UserEntity user = userRepository.findById(reviewRequestDTO.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new CommonException(ErrorCode.MUSICAL_NOT_FOUND));

        Review newReview = new Review();
        newReview.setContent(reviewRequestDTO.getContent());
        newReview.setUser(user);
        newReview.setMusical(musical);
        newReview.setCreatedAt(Timestamp.from(Instant.now()));
        newReview.setLike(0L);

        reviewRepository.save(newReview);
    }
}
