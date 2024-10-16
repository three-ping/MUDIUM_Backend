package com.threeping.mudium.musicalboard.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TBL_MUSICAL_BOARD_COMMENT")
@Entity(name = "musicalComment")
public class MusicalComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musical_board_comment_id")
    private Long commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "active_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    @Column(name = "musical_info_id")
    private Long musicalId;

    @Column(name = "user_id")
    private Long userId;

    public void softDelete() {
        this.activeStatus = ActiveStatus.INACTIVE;
    }
}
