package com.threeping.mudium.bookmark.entity;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<UserEntity> userId;

    @ManyToOne
    @JoinColumn(name = "musical_info_id")
    private Musical musicalInfoId;
}
