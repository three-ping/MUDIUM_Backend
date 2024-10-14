package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;

public interface CustomTicketService {

    CustomTicketDTO createCustomTicket(CustomTicketDTO customTicketDTO);

    CustomTicketDTO updateCustomTicket(Long customTicketId, CustomTicketDTO customTicketDTO);

    void deleteCustomTicket(Long ticketId);
}
