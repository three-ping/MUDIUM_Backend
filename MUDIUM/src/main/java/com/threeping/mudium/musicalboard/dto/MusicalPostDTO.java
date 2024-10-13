package com.threeping.mudium.musicalboard.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MusicalPostDTO {

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private Long like;

    private Long viewCount;

    private String nickname;

//    private List<MusicalComment> commentList;

//    private List<MusicalReply> replyList;
}
