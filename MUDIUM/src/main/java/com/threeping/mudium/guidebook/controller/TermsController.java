package com.threeping.mudium.guidebook.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.guidebook.dto.TermsRequestDTO;
import com.threeping.mudium.guidebook.entity.MusicalTerms;
import com.threeping.mudium.guidebook.service.terms.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/terms")
public class TermsController {

    private final TermsService termsService;

    @Autowired
    public TermsController ( TermsService termsService ) {
        this.termsService = termsService;
    }

    @PostMapping("/post")
    public ResponseDTO<?> createTerms ( @RequestBody TermsRequestDTO termsRequestDTO ){
        MusicalTerms createTerms = termsService.createTerms ( termsRequestDTO );
        return ResponseDTO.ok ( createTerms );
    }
}
