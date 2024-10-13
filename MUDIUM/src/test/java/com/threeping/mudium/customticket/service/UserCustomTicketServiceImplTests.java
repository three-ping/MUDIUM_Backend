package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;
import com.threeping.mudium.customticket.aggregate.entity.UserCustomTicketId;
import com.threeping.mudium.customticket.repository.UserCustomTicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserCustomTicketServiceImplTests {

    @Autowired
    private UserCustomTicketService userCustomTicketService;

    @Autowired
    private UserCustomTicketRepository userCustomTicketRepository;
    /**
     * 1. 회원 커스텀 티켓 생성 테스트
     */
    @Test
    public void testCreateUserCustomTicket() {
        // Given
        UserCustomTicketDTO newTicket = new UserCustomTicketDTO(
                1L, // User ID
                101L, // Custom Ticket ID
                "https://example.com/ticket.jpg",
                "VIP Access, Free Drink"
        );

        // When
        UserCustomTicketDTO createdTicket = userCustomTicketService.createUserCustomTicket(newTicket);

        // Then
        assertNotNull(createdTicket);
        assertEquals(1L, createdTicket.getUserId());
        assertEquals(101L, createdTicket.getCustomTicketId());
        assertEquals("https://example.com/ticket.jpg", createdTicket.getPhotoUrl());
        assertEquals("VIP Access, Free Drink", createdTicket.getTicketAttributers());
    }

    /**
     * 2. 회원 커스텀 티켓 수정 테스트
     */
    @Test
    public void testUpdateUserCustomTicket() {
        // Given - 먼저 티켓 생성
        UserCustomTicketDTO newTicket = new UserCustomTicketDTO(
                1L, 101L, "https://example.com/ticket.jpg", "VIP Access"
        );
        userCustomTicketService.createUserCustomTicket(newTicket);

        // 수정할 내용 준비
        UserCustomTicketDTO updateDTO = new UserCustomTicketDTO(
                1L, 101L, "https://example.com/updated-ticket.jpg", "VIP Lounge Access"
        );

        // When - 수정 요청
        UserCustomTicketDTO updatedTicket = userCustomTicketService.updateUserCustomTicket(
                1L, 101L, updateDTO
        );

        // Then - 수정 결과 검증
        assertNotNull(updatedTicket);
        assertEquals("https://example.com/updated-ticket.jpg", updatedTicket.getPhotoUrl());
        assertEquals("VIP Lounge Access", updatedTicket.getTicketAttributers());
    }

    /**
     * 3. 회원 커스텀 티켓 삭제 테스트
     */
    @Test
    public void testDeleteUserCustomTicket() {
        // Given - 먼저 티켓 생성
        UserCustomTicketDTO newTicket = new UserCustomTicketDTO(
                1L, 101L, "https://example.com/ticket.jpg", "VIP Access"
        );
        userCustomTicketService.createUserCustomTicket(newTicket);

        // When - 삭제 요청
        userCustomTicketService.deleteUserCustomTicket(1L, 101L);

        // Then - 삭제 결과 검증
        boolean isDeleted = userCustomTicketRepository.findById(new UserCustomTicketId(1L, 101L)).isEmpty();
        assertTrue(isDeleted); // 티켓이 삭제되었는지 확인
    }
}