package com.threeping.mudium.notice.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.notice.dto.NoticeDetailDTO;
import com.threeping.mudium.notice.dto.NoticeListDTO;
import com.threeping.mudium.notice.repository.NoticeRepository;
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
class NoticeServiceImplTests {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    @Autowired
    NoticeServiceImplTests(NoticeService noticeService,
                           NoticeRepository noticeRepository){
        this.noticeService = noticeService;
        this.noticeRepository = noticeRepository;
    }

    @DisplayName("공지 게시글 리스트를 페이지로 조회한다.")
    @Test
    void noticePageViewTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        Page<NoticeListDTO> noticeDTOPage = noticeService.viewBoardList(pageable);

        assertNotNull(noticeDTOPage, "조회된 페이지는 null이 아니다.");
        assertTrue(noticeDTOPage.hasContent(), "조회된 페이지는 내용이 있다.");
        assertEquals(10, noticeDTOPage.getSize(), "페이지 크기는 10이다.");
    }

    @DisplayName("공지 게시글 상세 정보를 조회한다.")
    @Test
    void noticeDetailViewTest() {
        Long noticeId = 1L;
        Long invalidNoticeId = -1L;

        NoticeDetailDTO firstNoticeDetail = noticeService.viewNotice(noticeId);

        assertNotNull(firstNoticeDetail, "조회된 공지사항은 null이 아니다.");
        assertEquals(noticeId, firstNoticeDetail.getNoticeId(), "조회된 공지사항 ID는 요청된 ID와 일치한다.");

        Exception exception = assertThrows(CommonException.class,
                () -> { noticeService.viewNotice(invalidNoticeId); });
        assertEquals("잘못된 공지게시글 번호입니다.", exception.getMessage());
    }
}
