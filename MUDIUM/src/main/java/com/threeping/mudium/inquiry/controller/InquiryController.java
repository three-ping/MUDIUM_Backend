package com.threeping.mudium.inquiry.controller;

import com.threeping.mudium.inquiry.aggregate.enumerate.SearchType;
import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.inquiry.dto.InquiryDetailDTO;
import com.threeping.mudium.inquiry.dto.InquiryListDTO;
import com.threeping.mudium.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    InquiryController(InquiryService inquiryService){
        this.inquiryService = inquiryService;
    }

    @GetMapping("{userId}")
    private ResponseDTO<?> viewInquiryPage(
            @PathVariable Long userId,
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchQuery,
            Pageable pageable){

        Page<InquiryListDTO> inquiryPage;

        if(searchType != null && searchQuery != null && !searchQuery.trim().isEmpty()) {
            inquiryPage = inquiryService.viewSearchedInquiryList(pageable,searchType,searchQuery,userId);
        } else {
            inquiryPage = inquiryService.viewInquiryList(pageable,userId);
        }

        return ResponseDTO.ok(inquiryPage);
    }

    @GetMapping("{userId}/{inquiryId}")
    private ResponseDTO<?> viewDetailInquiry(@PathVariable Long userId,
                                          @PathVariable Long inquiryId){
        InquiryDetailDTO inquiryDetail = inquiryService.viewInquiry(userId,inquiryId);
        return ResponseDTO.ok(inquiryDetail);
    }


}
