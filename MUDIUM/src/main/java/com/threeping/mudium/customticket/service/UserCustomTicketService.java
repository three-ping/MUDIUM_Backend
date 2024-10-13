package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;

public interface UserCustomTicketService {

    UserCustomTicketDTO createUserCustomTicket(UserCustomTicketDTO userCustomTicketDTO);
    UserCustomTicketDTO updateUserCustomTicket(Long userId, Long customTicketId, UserCustomTicketDTO userCustomTicketDTO);
    void deleteUserCustomTicket(Long userId, Long customTicketId);

}
