package com.threeping.mudium.boardcomment.service;

import com.threeping.mudium.board.aggregate.entity.Board;
import com.threeping.mudium.board.aggregate.enumerate.ActiveStatus;
import com.threeping.mudium.board.dto.RegistBoardDTO;
import com.threeping.mudium.board.repository.BoardRepository;
import com.threeping.mudium.board.service.BoardService;
import com.threeping.mudium.boardcomment.aggregate.BoardComment;
import com.threeping.mudium.boardcomment.dto.BoardCommentDTO;
import com.threeping.mudium.boardcomment.repository.BoardCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class BoardCommentServiceImplTests {

    private final BoardCommentService boardCommentService;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @Autowired
    BoardCommentServiceImplTests(BoardCommentService boardCommentService,
                                 BoardCommentRepository boardCommentRepository,
                                 BoardService boardService,
                                 BoardRepository boardRepository){
        this.boardCommentRepository = boardCommentRepository;
        this.boardCommentService = boardCommentService;
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }

    @DisplayName("자유게시글에 달린 댓글을 조회한다.")
    @Test
    void viewBoardCommentTest(){

        String title = "테스트 제목";
        String content = "테스트 내용";
        Long userId = 1L;
        RegistBoardDTO registBoardDTO = new RegistBoardDTO(userId,title,content);
        boardService.createBoard(registBoardDTO);
        Board savedBoard = boardRepository.findAll(Sort.by("createdAt").descending()).get(0);

        String commentContent = "테스트 댓글 내용";
        BoardCommentDTO boardCommentDTO = new BoardCommentDTO();
        boardCommentDTO.setBoardId(savedBoard.getBoardId());
        boardCommentDTO.setUserId(userId);
        boardCommentDTO.setContent(commentContent);
        boardCommentService.createBoardComment(boardCommentDTO);

        Page<BoardCommentDTO> boardCommentPage =
                boardCommentService.viewBoardComment
                        (savedBoard.getBoardId(),
                                PageRequest.of(1,
                                        10,
                                        Sort.by("createdAt").descending()));

        assertNotNull(boardCommentPage,"조회된 댓글은 null이 아니다.");
        assertTrue(boardCommentPage.hasContent(),"조회된 페이지에는 댓글이 존재한다.");
    }

    @DisplayName("자유게시글에 댓글을 생성한다.")
    @Test
    void createBoardCommentTest(){

        String title = "테스트 제목";
        String content = "테스트 내용";
        Long userId = 1L;
        RegistBoardDTO registBoardDTO = new RegistBoardDTO(userId,title,content);
        boardService.createBoard(registBoardDTO);
        Board savedBoard = boardRepository.findAll(Sort.by("createdAt").descending()).get(0);
        log.info("{}",savedBoard);
        String commentContent = "테스트 댓글 내용";
        BoardCommentDTO boardCommentDTO = new BoardCommentDTO();
        boardCommentDTO.setBoardId(savedBoard.getBoardId());
        boardCommentDTO.setUserId(userId);
        boardCommentDTO.setContent(commentContent);
        boardCommentDTO.setActiveStatus(ActiveStatus.ACTIVE);
        boardCommentDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        boardCommentService.createBoardComment(boardCommentDTO);
        log.info("{}",boardCommentDTO);

        BoardComment boardComment = boardCommentRepository
                .findByBoardIdAndActiveStatus(
                        savedBoard.getBoardId(), ActiveStatus.ACTIVE,
                        PageRequest.of(0,
                10,
                Sort.by("createdAt").descending())).getContent().get(0);

        assertNotNull(boardComment,"생성된 댓글은 null이 아니다.");
        assertEquals(commentContent,boardComment.getContent());
    }

}