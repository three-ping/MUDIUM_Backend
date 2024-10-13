package com.threeping.mudium.customticket.controller;
import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;
import com.threeping.mudium.customticket.service.UserCustomTicketService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-custom-ticket")
@Transactional
public class UserCustomTicketController {

    private final UserCustomTicketService userCustomTicketService;

    public UserCustomTicketController(UserCustomTicketService userCustomTicketService) {
        this.userCustomTicketService = userCustomTicketService;
    }

    @PostMapping("/create")
    public ResponseDTO<?> createUserCustomTicket(@RequestBody UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketDTO createdTicket = userCustomTicketService.createUserCustomTicket(userCustomTicketDTO);
        return ResponseDTO.ok(createdTicket);
    }

    @PutMapping("/update/{userId}/{customTicketId}")
    public ResponseDTO<?> updateUserCustomTicket(
            @PathVariable Long userId,
            @PathVariable Long customTicketId,
            @RequestBody UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketDTO updatedTicket = userCustomTicketService.updateUserCustomTicket(userId, customTicketId, userCustomTicketDTO);
        return ResponseDTO.ok(updatedTicket);
    }

    @DeleteMapping("/delete/{userId}/{customTicketId}")
    public ResponseDTO<?> deleteUserCustomTicket(
        @PathVariable Long userId,
        @PathVariable Long customTicketId) {
        userCustomTicketService.deleteUserCustomTicket(userId, customTicketId);
    return ResponseDTO.ok(null); // 삭제 시 데이터가 없으므로 null 반환
    }
}