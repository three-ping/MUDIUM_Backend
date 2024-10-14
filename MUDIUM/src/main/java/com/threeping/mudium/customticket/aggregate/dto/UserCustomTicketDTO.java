package com.threeping.mudium.customticket.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCustomTicketDTO {

    private Long userId;
    private Long customTicketId;     // 커스텀 티켓 ID
    private String photoUrl;
    private String ticketAttributers;
}
