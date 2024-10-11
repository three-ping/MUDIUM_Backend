package com.threeping.mudium.board.controller;

import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import com.threeping.mudium.board.service.BoardService;
import com.threeping.mudium.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private BoardService boardService;

    @Autowired
    private BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("")
    private ResponseDTO<?> viewBoardPage(Pageable pageable){
        Page<BoardListDTO> boardPage = boardService.viewBoardList(pageable);
        return ResponseDTO.ok(boardPage);
    }

    @GetMapping("/detail/{boardId}")
    private ResponseDTO<?> viewDetailBoard(@PathVariable Long boardId){
        BoardDetailDTO boardDetail = boardService.viewBoard(boardId);
        return ResponseDTO.ok(boardDetail);
    }
}
