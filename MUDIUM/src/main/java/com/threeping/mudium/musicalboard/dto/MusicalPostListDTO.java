package com.threeping.mudium.musicalboard.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MusicalPostListDTO {

    private Long postId;

    private String title;

    private String writer;

    private Long comment;

    private Long like;

    private String viewCount;

    private String createdAt;
}
