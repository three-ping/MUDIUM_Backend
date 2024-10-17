package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;
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
        Musical musical = new Musical(1L, "Test Musical", "전체관람가", "url", "poster", 1L, "production", "synopsys");
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
        Musical musical = new Musical(1L, "Test Musical", "전체관람가", "url", "poster", 1L, "production", "synopsys");
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
        Musical musical = new Musical(1L, "Test Musical", "전체관람가", "url", "poster", 1L, "production", "synopsys");
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
