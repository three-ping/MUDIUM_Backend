package com.threeping.mudium.review.repository;

import com.threeping.mudium.review.aggregate.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
