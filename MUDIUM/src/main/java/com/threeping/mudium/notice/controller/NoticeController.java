package com.threeping.mudium.notice.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    private NoticeController(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    @GetMapping("")
    private ResponseDTO<?> findAllNotice(){

        return ResponseDTO.ok(null);
    }
}
