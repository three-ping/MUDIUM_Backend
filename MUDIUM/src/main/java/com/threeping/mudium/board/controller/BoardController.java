package com.threeping.mudium.board.controller;

import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import com.threeping.mudium.board.dto.RegistBoardDTO;
import com.threeping.mudium.board.dto.UpdateBoardDTO;
import com.threeping.mudium.board.service.BoardService;
import com.threeping.mudium.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{boardId}")
    private ResponseDTO<?> viewDetailBoard(@PathVariable Long boardId){
        BoardDetailDTO boardDetail = boardService.viewBoard(boardId);
        return ResponseDTO.ok(boardDetail);
    }

    @PostMapping("")
    private ResponseDTO<?> createBoard(@RequestBody RegistBoardDTO registBoardDTO){
        boardService.createBoard(registBoardDTO);
        return ResponseDTO.ok(null);
    }

    @PutMapping("{boardId}")
    private ResponseDTO<?> updateBoard(@PathVariable Long boardId,
                                       @RequestBody UpdateBoardDTO updateBoardDTO){
        updateBoardDTO.setBoardId(boardId);
        boardService.updateBoard(updateBoardDTO);
        return ResponseDTO.ok(null);
    }

    @DeleteMapping("{boardId}")
    private ResponseDTO<?> deleteBoard(@PathVariable Long boardId,
                                       @RequestBody UpdateBoardDTO updateBoardDTO){
        updateBoardDTO.setBoardId(boardId);
        boardService.deleteBoard(updateBoardDTO);
        return ResponseDTO.ok(null);
    }}
