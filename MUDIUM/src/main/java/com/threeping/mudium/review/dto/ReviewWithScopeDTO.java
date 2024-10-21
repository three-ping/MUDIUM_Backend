package com.threeping.mudium.review.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewWithScopeDTO {

    private Long reviewId;

    private String content;

    private Double scope;

    private Long userId;

    private String nickName;

    private Long musicalId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Long like;

    private Long comments;

    private String musicalTitle;

}
