package com.threeping.mudium.review.repository;

import com.threeping.mudium.review.aggregate.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT o FROM Review o " +
            "JOIN o.musical c " +
//            "ON o.musicalId = c.musicalId " +
            "WHERE c.musicalId = :musicalId")
    List<Review> findReviewByMusicalId(@Param("musicalId") Long musicalId);
}
