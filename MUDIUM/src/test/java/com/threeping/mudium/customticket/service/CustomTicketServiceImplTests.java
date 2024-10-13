//package com.threeping.mudium.customticket.service;
//
//import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;
//import com.threeping.mudium.customticket.aggregate.entity.CustomTicketEntity;
//import com.threeping.mudium.customticket.repository.CustomTicketRepository;
//import com.threeping.mudium.musical.aggregate.Musical;
//import com.threeping.mudium.musical.repository.MusicalRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class CustomTicketServiceImplTests {
//
//    @Autowired
//    private CustomTicketService customTicketService;
//
//    @Autowired
//    private CustomTicketRepository customTicketRepository;
//
//    @Autowired
//    private MusicalRepository musicalRepository;
//
//    // 커스텀 티켓 생성 테스트
//    @Test
//    public void testCreateCustomTicket() {
//        // 이미 존재하는 뮤지컬 ID 사용 (예: 1L)
//        Long musicalId = 1L;
//        CustomTicketDTO customTicketDTO = new CustomTicketDTO(
//                null,
//                "https://example.com/ticket.jpg",
//                "테마 이름",
//                musicalId
//        );
//
//        // 티켓 생성
//        CustomTicketEntity ticket = customTicketService.createCustomTicket(customTicketDTO);
//
//        // 생성된 티켓 검증
//        assertNotNull(ticket);
//        assertNotNull(ticket.getCustomTicketId());
//        assertEquals("테마 이름", ticket.getThemeName());
//        assertEquals(musicalId, ticket.getMusical().getMusicalId());
//    }
//
//    // 커스텀 티켓 수정 테스트
//    @Test
//    public void testUpdateCustomTicket() {
//        // 이미 존재하는 티켓 ID 사용 (예: 1L)
//        Long ticketId = 1L;
//
//        // 수정할 내용 생성
//        CustomTicketDTO updatedDTO = new CustomTicketDTO(
//                ticketId,
//                "https://example.com/updated.jpg",
//                "수정된 테마 이름",
//                1L // 기존 뮤지컬 ID 사용
//        );
//
//        // 티켓 수정
//        CustomTicketEntity updatedTicket = customTicketService.updateCustomTicket(ticketId, updatedDTO);
//
//        // 수정된 티켓 검증
//        assertNotNull(updatedTicket);
//        assertEquals("수정된 테마 이름", updatedTicket.getThemeName());
//        assertEquals("https://example.com/updated.jpg", updatedTicket.getTicketImage());
//    }
//
//    // 커스텀 티켓 삭제 테스트
//    @Test
//    public void testDeleteCustomTicket() {
//        // 이미 존재하는 티켓 ID 사용 (예: 1L)
//        Long ticketId = 1L;
//
//        // 티켓 삭제
//        customTicketService.deleteCustomTicket(ticketId);
//
//        // 삭제 후 확인
//        boolean isDeleted = customTicketRepository.findById(ticketId).isEmpty();
//        assertTrue(isDeleted);  // 티켓이 삭제되었는지 확인
//    }
//}
package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;
import com.threeping.mudium.customticket.aggregate.entity.CustomTicketEntity;
import com.threeping.mudium.customticket.repository.CustomTicketRepository;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.repository.MusicalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomTicketServiceImplTests {

    @Autowired
    private CustomTicketService customTicketService;

    @Autowired
    private CustomTicketRepository customTicketRepository;

    @Autowired
    private MusicalRepository musicalRepository;

    // **1. 커스텀 티켓 생성 테스트**
    @Test
    public void testCreateCustomTicket() {
        // 테스트용 뮤지컬 생성 및 저장
        Musical musical = new Musical(null, "Test Musical", "4.5", "url", "poster", 0);
        musical = musicalRepository.save(musical); // 저장된 뮤지컬 객체

        // 커스텀 티켓 생성
        CustomTicketDTO customTicketDTO = new CustomTicketDTO(
                null,
                "https://example.com/ticket.jpg",
                "테마 이름",
                musical.getMusicalId()
        );

        // 생성 및 검증
        CustomTicketDTO createdTicket = customTicketService.createCustomTicket(customTicketDTO);
        assertNotNull(createdTicket);
        assertNotNull(createdTicket.getCustomTicketId());
        assertEquals("테마 이름", createdTicket.getThemeName());
    }

    // **2. 커스텀 티켓 수정 테스트**
    @Test
    public void testUpdateCustomTicket() {
        // 먼저 생성된 뮤지컬과 티켓 저장
        Musical musical = new Musical(null, "Test Musical", "4.5", "url", "poster", 0);
        musical = musicalRepository.save(musical);

        CustomTicketDTO customTicketDTO = new CustomTicketDTO(
                null,
                "https://example.com/ticket.jpg",
                "테마 이름",
                musical.getMusicalId()
        );

        // 티켓 생성
        CustomTicketDTO createdTicket = customTicketService.createCustomTicket(customTicketDTO);

        // 수정할 내용 설정
        CustomTicketDTO updatedDTO = new CustomTicketDTO(
                createdTicket.getCustomTicketId(),
                "https://example.com/updated.jpg",
                "수정된 테마 이름",
                musical.getMusicalId()
        );

        // 티켓 수정 및 검증
        CustomTicketDTO updatedTicket = customTicketService.updateCustomTicket(
                createdTicket.getCustomTicketId(), updatedDTO);

        assertNotNull(updatedTicket);
        assertEquals("수정된 테마 이름", updatedTicket.getThemeName());
        assertEquals("https://example.com/updated.jpg", updatedTicket.getTicketImage());
    }

    // **3. 커스텀 티켓 삭제 테스트**
    @Test
    public void testDeleteCustomTicket() {
        // 먼저 생성된 뮤지컬과 티켓 저장
        Musical musical = new Musical(null, "Test Musical", "4.5", "url", "poster", 0);
        musical = musicalRepository.save(musical);

        CustomTicketDTO customTicketDTO = new CustomTicketDTO(
                null,
                "https://example.com/ticket.jpg",
                "테마 이름",
                musical.getMusicalId()
        );

        // 티켓 생성
        CustomTicketDTO createdTicket = customTicketService.createCustomTicket(customTicketDTO);

        // 티켓 삭제
        customTicketService.deleteCustomTicket(createdTicket.getCustomTicketId());

        // 삭제 후 확인
        boolean isDeleted = customTicketRepository.findById(createdTicket.getCustomTicketId()).isEmpty();
        assertTrue(isDeleted);
    }
}
