package com.threeping.mudium.customticket.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomTicketDTO {

    private Long customTicketId;
    private String ticketImage;
    private String themeName;
    private Long musicalId;
}
