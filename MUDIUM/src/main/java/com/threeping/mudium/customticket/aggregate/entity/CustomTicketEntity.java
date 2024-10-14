package com.threeping.mudium.customticket.aggregate.entity;

import com.threeping.mudium.musical.aggregate.Musical;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tbl_custom_ticket")
public class CustomTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="custom_ticket_id")
    private Long customTicketId;

    @Column(name="ticket_image")
    private String ticketImage;

    @Column(name="theme_name")
    private String themeName;

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계 설정
    @JoinColumn(name="musical_info_id", nullable=false)
    private Musical musical;

}
