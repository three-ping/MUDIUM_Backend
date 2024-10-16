package com.threeping.mudium.bookmark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_BOOKMARK")
@IdClass ( BookmarkPK.class )
public class Bookmark implements Serializable {

    // 회원-북마크 1:N
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 북마크-뮤지컬정보 N:1
    @Id
    @Column(name = "musical_info_id", nullable = false)
    private Long musicalId;
}
