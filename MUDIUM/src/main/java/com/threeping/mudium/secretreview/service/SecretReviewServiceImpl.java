package com.threeping.mudium.secretreview.service;

import com.threeping.mudium.review.repository.ReviewRepository;
import com.threeping.mudium.secretreview.aggregate.entity.ActiveStatus;
import com.threeping.mudium.secretreview.aggregate.entity.SecretReview;
import com.threeping.mudium.secretreview.dto.SecretReviewResponseDTO;
import com.threeping.mudium.secretreview.repository.SecretReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecretReviewServiceImpl implements SecretReviewService{

    private final SecretReviewRepository secretReviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SecretReviewServiceImpl(SecretReviewRepository secretReviewRepository, ModelMapper modelMapper, ReviewRepository reviewRepository) {
        this.secretReviewRepository = secretReviewRepository;
        this.modelMapper = modelMapper;
    }

    // 비밀리뷰 전체 조회
    @Override
    public List<SecretReviewResponseDTO> findSecretReviewByUserId(Long userId) {

        List<SecretReview> secretReviews = secretReviewRepository
                .findAllByUser_UserIdAndActiveStatus(userId, ActiveStatus.ACTIVE);

        List<SecretReviewResponseDTO> secretReviewResponseDTO = secretReviews.stream()
                .map(secretReview -> modelMapper.map(secretReview, SecretReviewResponseDTO.class))
                .collect(Collectors.toList());

        return secretReviewResponseDTO;
    }

    // 비밀리뷰 상세 조회
    @Override
    public List<SecretReviewResponseDTO> findSecretReviewByUserIdAndSecretReviewId(Long userId, Long secretReviewId) {

        List<SecretReview> secretReviews = secretReviewRepository
                .findByUser_UserIdAndSecretReviewIdAndActiveStatus(userId, secretReviewId, ActiveStatus.ACTIVE);

        List<SecretReviewResponseDTO> secretReviewResponseDTO = secretReviews.stream()
                .map(secretReview -> modelMapper.map(secretReview, SecretReviewResponseDTO.class))
                .collect(Collectors.toList());

        return secretReviewResponseDTO;
    }
}
