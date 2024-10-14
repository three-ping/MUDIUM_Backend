package com.threeping.mudium.review.dto;

import com.threeping.mudium.review.aggregate.entity.ActiveStatus;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewResponseDTO {
    private Long reviewId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long like;
    private ActiveStatus activeStatus;
    private Long musicalId;
    private Long userId;
    private String userNickname;
}
