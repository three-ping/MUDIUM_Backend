package com.threeping.mudium.board.service;

import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import com.threeping.mudium.common.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTests {

    private final BoardService boardService;

    @Autowired
    BoardServiceImplTests(BoardService boardService){
        this.boardService = boardService;
    }

    @DisplayName("게시글 리스트를 페이지로 조회한다.")
    @Test
    void boardPageViewTest(){
        Pageable pageable = PageRequest.of(9999,10, Sort.by("createdAt").descending());

        Page<BoardListDTO> boardDTOPage = boardService.viewBoardList(pageable);

        assertNotNull(boardDTOPage, "조회된 페이지는 null이 아니다.");
        assertTrue(boardDTOPage.hasContent(), "조회된 페이지는 내용이 있다.");
        assertEquals(10, boardDTOPage.getSize(), "페이지 크기는 10이다.");

    }

    @DisplayName("게시글 상세 정보를 조회한다.")
    @Test
    void boardDetailViewTest(){
        Long boardId = 1L;
        Long invalidBoardId = -1L;

        BoardDetailDTO firstBoardDetail = boardService.viewBoard(boardId);

        assertNotNull(firstBoardDetail,"조회된 게시글은 null이 아니다.");
        assertEquals(boardId,firstBoardDetail.getId(),"조회된 게시글 ID는 요청된 ID와 일치한다.");
        Exception exception = assertThrows(CommonException.class,
                ()->{boardService.viewBoard(invalidBoardId);});
        assertEquals("잘못된 자유게시글 번호입니다.",exception.getMessage());

    }

}