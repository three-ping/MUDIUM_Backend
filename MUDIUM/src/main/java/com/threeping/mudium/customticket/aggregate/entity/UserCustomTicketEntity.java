package com.threeping.mudium.customticket.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tbl_user_custom_ticket")
@IdClass(UserCustomTicketId.class)
public class UserCustomTicketEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "custom_ticket_id")
    private Long customTicketId;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "ticekt_attriburters", nullable = false)
    private String ticketAttributers;
}
