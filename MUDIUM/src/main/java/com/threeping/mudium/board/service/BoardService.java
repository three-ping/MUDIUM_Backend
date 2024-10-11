package com.threeping.mudium.board.service;

import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Page<BoardListDTO> viewBoardList(Pageable pageable);

    BoardDetailDTO viewBoard(Long boardId);
}
