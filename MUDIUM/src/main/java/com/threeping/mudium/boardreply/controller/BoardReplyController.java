package com.threeping.mudium.boardreply.controller;

import com.threeping.mudium.boardreply.dto.BoardReplyDTO;
import com.threeping.mudium.boardreply.service.BoardReplyService;
import com.threeping.mudium.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board-reply")
public class BoardReplyController {

    private final BoardReplyService boardReplyService;

    @Autowired
    BoardReplyController(BoardReplyService boardReplyService) {
        this.boardReplyService = boardReplyService;
    }

    @GetMapping("{boardCommentId}")
    private ResponseDTO<?> viewBoardReply(@PathVariable Long boardCommentId){
        List<BoardReplyDTO> boardReplies = boardReplyService.viewBoardReply(boardCommentId);

        return ResponseDTO.ok(boardReplies);
    }

    @PostMapping("{boardCommentId}")
    private ResponseDTO<?> createBoardReply(@PathVariable Long boardCommentId,
                                            @RequestBody BoardReplyDTO boardReplyDTO){
        boardReplyDTO.setBoardCommentId(boardCommentId);
        boardReplyService.createBoardReply(boardReplyDTO);

        return ResponseDTO.ok(null);
    }
}
