package com.threeping.mudium.musicalboard.aggregate;

import com.threeping.mudium.user.aggregate.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "musicalReply")
@Table(name = "TBL_MUSICAL_BOARD_REPLY")
public class MusicalReply {

    @Id
    @Column(name = "musical_board_reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "active_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    @JoinColumn(name = "musical_board_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MusicalComment comment;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public void softDelete() {
        this.activeStatus = ActiveStatus.INACTIVE;
    }
}
