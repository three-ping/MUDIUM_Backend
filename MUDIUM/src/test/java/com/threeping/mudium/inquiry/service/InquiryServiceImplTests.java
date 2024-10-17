package com.threeping.mudium.inquiry.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.inquiry.dto.InquiryDetailDTO;
import com.threeping.mudium.inquiry.dto.InquiryListDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class InquiryServiceImplTests {

    private final InquiryService inquiryService;
    @Autowired
    InquiryServiceImplTests(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @DisplayName("게시글 리스트를 페이지로 조회한다.")
    @Test
    void inquiryPageViewTest(){
        Pageable pageable = PageRequest.of(1,10, Sort.by("createdAt").descending());
        Long memberId = 1L;
        Page<InquiryListDTO> inquiryDTOPage = inquiryService.viewInquiryList(pageable,memberId);

        assertNotNull(inquiryDTOPage, "조회된 페이지는 null이 아니다.");
        log.info("inquiryDTOPage: {}",inquiryDTOPage);
        assertTrue(inquiryDTOPage.hasContent(), "조회된 페이지는 내용이 있다.");
        assertEquals(10, inquiryDTOPage.getSize(), "페이지 크기는 10이다.");

    }

    @DisplayName("게시글 상세 정보를 조회한다.")
    @Test
    void inquiryDetailViewTest(){
        Long inquiryId = 1L;
        Long invalidInquiryId = -1L;
        Long memberId = 1L;
        InquiryDetailDTO inquiryDetailDTO = inquiryService.viewInquiry(memberId,inquiryId);

        assertNotNull(inquiryDetailDTO,"조회된 게시글은 null이 아니다.");
        assertEquals(inquiryId,inquiryDetailDTO.getInquiryId(),"조회된 게시글 ID는 요청된 ID와 일치한다.");
        Exception exception = assertThrows(CommonException.class,
                ()->inquiryService.viewInquiry(memberId,invalidInquiryId));
        assertEquals("해당 문의를 찾을 수 없습니다.",exception.getMessage());
    }
}