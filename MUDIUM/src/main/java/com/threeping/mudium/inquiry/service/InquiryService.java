package com.threeping.mudium.inquiry.service;

import com.threeping.mudium.inquiry.aggregate.enumerate.SearchType;
import com.threeping.mudium.inquiry.dto.InquiryDetailDTO;
import com.threeping.mudium.inquiry.dto.InquiryListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryService {
    Page<InquiryListDTO> viewSearchedInquiryList(Pageable pageable, SearchType searchType, String searchQuery, Long userId);

    Page<InquiryListDTO> viewInquiryList(Pageable pageable, Long userId);

    InquiryDetailDTO viewInquiry(Long userId, Long inquiryId);
}
