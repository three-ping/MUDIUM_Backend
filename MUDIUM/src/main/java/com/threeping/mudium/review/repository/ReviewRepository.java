package com.threeping.mudium.review.repository;

import com.threeping.mudium.review.aggregate.entity.ActiveStatus;
import com.threeping.mudium.review.aggregate.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 전체 조회
    List<Review> findAllByMusical_MusicalIdAndActiveStatus(Long musicalId, ActiveStatus activeStatus);

    // 리뷰 상세 조회
    List<Review> findByMusical_MusicalIdAndReviewIdAndActiveStatus(Long musicalId, Long reviewId, ActiveStatus activeStatus);
}
