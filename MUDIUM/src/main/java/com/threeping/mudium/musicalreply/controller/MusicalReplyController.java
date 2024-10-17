package com.threeping.mudium.musicalreply.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.musicalreply.dto.MusicalReplyDTO;
import com.threeping.mudium.musicalreply.service.MusicalReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/musical-reply")
public class MusicalReplyController {

    private final MusicalReplyService musicalReplyService;

    @Autowired
    public MusicalReplyController(MusicalReplyService musicalReplyService) {
        this.musicalReplyService = musicalReplyService;
    }

    @GetMapping("{commentId}")
    public ResponseDTO<?> findMusicalReply(@PathVariable Long commentId) {
        List<MusicalReplyDTO> list = musicalReplyService.findAllReply(commentId);

        ResponseDTO<List<MusicalReplyDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(list);
        responseDTO.setSuccess(true);
        responseDTO.setHttpStatus(HttpStatus.OK);
        return responseDTO;
    }
}
