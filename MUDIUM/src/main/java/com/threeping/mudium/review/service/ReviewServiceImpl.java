package com.threeping.mudium.review.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.repository.MusicalRepository;
import com.threeping.mudium.review.dto.ReviewRequestDTO;
import com.threeping.mudium.review.dto.ReviewResponseDTO;
import com.threeping.mudium.review.aggregate.entity.ActiveStatus;
import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.review.dto.ReviewWithScopeDTO;
import com.threeping.mudium.review.repository.ReviewRepository;
import com.threeping.mudium.scope.service.ScopeService;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import com.threeping.mudium.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ScopeService scopeService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper,
                             UserRepository userRepository,
                             MusicalRepository musicalRepository,
                             ScopeService scopeService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.musicalRepository = musicalRepository;
        this.scopeService = scopeService;
    }

    // 리뷰 전체 조회
    @Override
    public List<ReviewResponseDTO> findReviewByMusicalId(Long musicalId) {

        /* 필기. 엔티티에 @ManyToOne이 있어서 JPA를 해당 메소드로 가능 */
        List<Review> reviews = reviewRepository.findAllByMusical_MusicalIdAndActiveStatus(musicalId, ActiveStatus.ACTIVE);

        /* 필기. Stream API 사용 */
//        List<ReviewResponseDTO> reviewResponseDTO = reviews.stream()
//                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
//                .collect(Collectors.toList());
//
//        return reviewResponseDTO;
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewWithScopeDTO> findReviewsWithRatingsByMusicalId(Long musicalId) {
        return reviewRepository.findReviewsWithRatingsByMusicalId(musicalId);
    }

    private ReviewResponseDTO convertToDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewId(review.getReviewId());
        dto.setContent(review.getContent());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setLike(review.getLike());
        dto.setActiveStatus(review.getActiveStatus());
        dto.setMusicalId(review.getMusical().getMusicalId());
        dto.setUserId(review.getUser().getUserId());
        dto.setUserProfile(review.getUser().getProfileImage());
        dto.setUserNickname(review.getUser().getNickname());
        dto.setMusicalTitle(review.getMusical().getTitle());

        return dto;
    }

    // 리뷰 상세 조회
    @Override
    public List<ReviewResponseDTO> findReviewByMusicalIdAndReviewId(Long musicalId, Long reviewId) {

        List<Review> reviews = reviewRepository.findByMusical_MusicalIdAndReviewIdAndActiveStatus(
                musicalId, reviewId, ActiveStatus.ACTIVE);

        List<ReviewResponseDTO> reviewResponseDTO = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
                .collect(Collectors.toList());

        return reviewResponseDTO;
    }

    // 리뷰 작성
    @Override
    public void createReview(Long musicalId, ReviewRequestDTO reviewRequestDTO) {

        List<Review> reviews = reviewRepository.findAllByMusical_MusicalIdAndActiveStatus(
                musicalId, ActiveStatus.ACTIVE);

        // 뮤지컬 중에서 userId가 없거나 만약 있으면 활성화 상태가 비활성화이면 리뷰 작성 / userId가 있고 활성화 상태가 활성화면 리뷰 작성 못하게.
        boolean hasActiveReview = reviews.stream()
                .anyMatch(review -> review.getUser().getUserId().equals(
                        reviewRequestDTO.getUserId()));

        if (hasActiveReview) {
            throw new CommonException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        UserEntity user = userRepository.findById(reviewRequestDTO.getUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
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

    // 리뷰 수정
    @Override
    public void updateReview(Long musicalId, Long reviewId, ReviewRequestDTO reviewRequestDTO) {

        List<Review> review = reviewRepository.findByMusical_MusicalIdAndReviewIdAndActiveStatus(
                musicalId, reviewId, ActiveStatus.ACTIVE);

        // 리뷰가 존재하지 않으면 처리!
        if (review.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_REVIEW);
        }

        // userId를 체크
        boolean checkUser = review.stream()
                .anyMatch(m -> m.getUser().getUserId().equals(reviewRequestDTO.getUserId()));

        // userId가 다르거나 리뷰가 활성화가 아니면 처리!
        if (!checkUser) {
            throw new CommonException(ErrorCode.WRONG_ENTRY_POINT);
        }

        // 존재한다면 리뷰는 한 개! 거기다 new가 아니라 그대로 가져와야 나머지 부분 수정 안해도 된다.
        // Review newReview = new Review();
        Review newReview = review.get(0);
        newReview.setContent(reviewRequestDTO.getContent());
        newReview.setUpdatedAt(Timestamp.from(Instant.now()));

        reviewRepository.save(newReview);
    }

    // 리뷰 삭제
    @Override
    public void deleteReview(Long musicalId, Long reviewId, Long userId) {

        List<Review> review = reviewRepository.findByMusical_MusicalIdAndReviewIdAndActiveStatus(
                musicalId, reviewId, ActiveStatus.ACTIVE);

        // 리뷰가 존재하지 않으면 처리!
        if (review.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_REVIEW);
        }

        // userId를 체크
        boolean checkUser = review.stream()
                .anyMatch(m -> m.getUser().getUserId().equals(userId));

        // userId가 다르거나 리뷰가 활성화가 아니면 처리!
        if (!checkUser) {
            throw new CommonException(ErrorCode.WRONG_ENTRY_POINT);
        }

        Review newReview = review.get(0);
        newReview.deactivateReview();

        reviewRepository.save(newReview);
    }
}
