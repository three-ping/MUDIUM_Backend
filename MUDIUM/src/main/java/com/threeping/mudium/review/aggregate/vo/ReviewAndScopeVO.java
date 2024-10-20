package com.threeping.mudium.review.aggregate.vo;

import com.threeping.mudium.review.aggregate.entity.Review;
import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAndScopeVO {
    private Long musicalId;
    private Long userId;
    private Double scope;
    private String userNickname;
    private Long reviewId;
    private String reviewContent;
    private Timestamp reviewCreatedAt;
    private Long reviewLikes;

    public static ReviewAndScopeVO from(ScopeEntity scope, Review review) {
        ReviewAndScopeVO vo = new ReviewAndScopeVO();

        if (scope != null) {
            vo.setMusicalId(scope.getMusicalId());
            vo.setUserId(scope.getUserId());
            vo.setScope(scope.getScope());
            vo.setUserNickname(scope.getUserNickname());
        }

        if (review != null) {
            vo.setMusicalId(vo.getMusicalId() != null ? vo.getMusicalId() : review.getMusical().getMusicalId());
            vo.setUserId(vo.getUserId() != null ? vo.getUserId() : review.getUser().getUserId());
            vo.setUserNickname(vo.getUserNickname() != null ? vo.getUserNickname() : review.getUser().getNickname());
            vo.setReviewId(review.getReviewId());
            vo.setReviewContent(review.getContent());
            vo.setReviewCreatedAt(review.getCreatedAt());
            vo.setReviewLikes(review.getLike());
        }

        return vo;
    }
}