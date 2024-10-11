package com.threeping.mudium.review.aggregate.entity;

import com.threeping.mudium.user.aggregate.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "TBL_REVIEW")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "like", nullable = false)
    private Long like;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status", nullable = false)
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    // 외래 키 매핑 (ManyToOne)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "musical_id")  // FK 컬럼을 명시
//    private MusicalEntity musicalId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")  // FK 컬럼을 명시
//    private UserEntity userId;

    public void deactivateReview() {
        this.activeStatus = ActiveStatus.INACTIVE;
    }

    // 다시 생성하는데 필요한지는 의문.
    public void activateReview() {
        this.activeStatus = ActiveStatus.ACTIVE;
    }
}
