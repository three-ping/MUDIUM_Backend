package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;

import java.util.List;

public interface CustomTicketService {

    CustomTicketDTO createCustomTicket(CustomTicketDTO customTicketDTO);

    CustomTicketDTO updateCustomTicket(Long customTicketId, CustomTicketDTO customTicketDTO);

    void deleteCustomTicket(Long ticketId);

    List<CustomTicketDTO> getAllCustomTickets();
}
