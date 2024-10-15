package com.threeping.mudium.boardlike.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardLikeServiceImplTests {

    private final BoardLikeService boardLikeService;

    @Autowired
    BoardLikeServiceImplTests(BoardLikeService boardLikeService){
        this.boardLikeService = boardLikeService;
    }

    @DisplayName("자유게시글 좋아요를 조회한다.")
    @Test
    void findBoardLikeTest(){}

    @DisplayName("자유게시글 좋아요를 등록한다.")
    @Test
    void createBoardLikeTest(){}

    @DisplayName("자유게시글 좋아요를 삭제한다.")
    @Test
    void deleteBoardLikeTest(){}


}