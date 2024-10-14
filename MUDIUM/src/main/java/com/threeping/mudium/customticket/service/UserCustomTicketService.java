package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;

import java.util.List;

public interface UserCustomTicketService {

    UserCustomTicketDTO createUserCustomTicket(UserCustomTicketDTO userCustomTicketDTO);
    UserCustomTicketDTO updateUserCustomTicket(Long userId, Long customTicketId, UserCustomTicketDTO userCustomTicketDTO);
    void deleteUserCustomTicket(Long userId, Long customTicketId);
    List<UserCustomTicketDTO> findUserCustomTickets(Long userId);

}
