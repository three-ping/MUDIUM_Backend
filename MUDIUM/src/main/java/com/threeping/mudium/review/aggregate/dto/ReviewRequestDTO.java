package com.threeping.mudium.review.aggregate.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewRequestDTO {
    private Long reviewId;
    private String content;
    private Timestamp createdAt;

}
