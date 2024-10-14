package com.threeping.mudium.customticket.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;
import com.threeping.mudium.customticket.service.CustomTicketService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customticket")
@Transactional
public class CustomTicketController {

    private final CustomTicketService customTicketService;

    public CustomTicketController(CustomTicketService customTicketService) {
        this.customTicketService = customTicketService;
    }

    // 티켓 생성
    @PostMapping("/create")
    public ResponseDTO<?> createCustomTicket(@RequestBody CustomTicketDTO customTicketDTO) {
        return ResponseDTO.ok(customTicketService.createCustomTicket(customTicketDTO));
    }

    // 커스텀 티켓 수정
    @PutMapping("/update/{ticketId}")
    public ResponseDTO<?> updateCustomTicket(@PathVariable Long ticketId,
                                             @RequestBody CustomTicketDTO customTicketDTO) {
        return ResponseDTO.ok(customTicketService.updateCustomTicket(ticketId, customTicketDTO));
    }

    // 커스텀 티켓 삭제
    @DeleteMapping("/delete/{ticketId}")
    public ResponseDTO<?> deleteCustomTicket(@PathVariable Long ticketId) {
        customTicketService.deleteCustomTicket(ticketId);
        return ResponseDTO.ok(null);
    }
}
