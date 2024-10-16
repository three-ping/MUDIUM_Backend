package com.threeping.mudium.notice.service;

import com.threeping.mudium.notice.aggregate.enumerate.SearchType;
import com.threeping.mudium.notice.dto.CreateNoticeDTO;
import com.threeping.mudium.notice.dto.NoticeDetailDTO;
import com.threeping.mudium.notice.dto.NoticeListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Page<NoticeListDTO> viewSearchedNoticeList(Pageable pageable, SearchType searchType, String searchQuery);

    Page<NoticeListDTO> viewBoardList(Pageable pageable);

    NoticeDetailDTO viewNotice(Long noticeId);

    void createNotice(CreateNoticeDTO createNoticeDTO);
}
