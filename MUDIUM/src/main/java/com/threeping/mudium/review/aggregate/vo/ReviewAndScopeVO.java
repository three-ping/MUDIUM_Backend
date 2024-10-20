package com.threeping.mudium.review.aggregate.vo;

import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
import lombok.*;

import java.sql.Timestamp;

@Data
public class ReviewAndScopeVO {
    private Long musicalId;
    private Long userId;
    private Double scope;
    private String userNickname;
    private Long reviewId;
    private String reviewContent;
    private Timestamp reviewCreatedAt;
    private Long reviewLikes;

    public ReviewAndScopeVO(Long musicalId, Long userId, Double scope, String userNickname, Long aLong, String s, Timestamp timestamp, Long aLong1) {
    }

    public static ReviewAndScopeVO from(ScopeEntity scope, Review review) {
        return new ReviewAndScopeVO(
                scope != null ? scope.getMusicalId() : (review != null ? review.getMusical().getMusicalId() : null),
                scope != null ? scope.getUserId() : (review != null ? review.getUser().getUserId() : null),
                scope != null ? scope.getScope() : null,
                scope != null ? scope.getUserNickname() : null,
                review != null ? review.getReviewId() : null,
                review != null ? review.getContent() : null,
                review != null ? review.getCreatedAt() : null,
                review != null ? review.getLike() : null
        );
    }
    }

