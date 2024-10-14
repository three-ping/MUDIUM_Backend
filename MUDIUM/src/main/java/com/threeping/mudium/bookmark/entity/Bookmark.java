package com.threeping.mudium.bookmark.entity;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_BOOKMARK")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    // 회원-북마크 1:N
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    // 북마크-뮤지컬정보 N:1
    @ManyToOne
    @JoinColumn(name = "musical_info_id")
    private Musical musicalInfoId;
}
