package com.threeping.mudium.musicalboard.service;

import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
class MusicalBoardServiceImplTests {

    private final MusicalBoardService musicalBoardService;

    @Autowired
    public MusicalBoardServiceImplTests(MusicalBoardService musicalBoardService) {
        this.musicalBoardService = musicalBoardService;
    }

    @DisplayName("특정 뮤지컬 게시글 전부 조회한다.")
    @Test
    void findAllPosts() {
        // given
        Long musicalBoardId = 1L;

        // when
        List<MusicalPostListDTO> postList = musicalBoardService.findAllPost(musicalBoardId);

        // then
        assertNotNull(postList, "게시글 리스트는 null이 아니다.");
        assertFalse(postList.isEmpty(), "게시글 리스트는 비어있지 않다.");
    }

    @DisplayName("특정 뮤지컬 게시판의 특정 게시글을 조회한다.")
    @Test
    void findPostById() {
        // given
        Long musicalBoardId = 31L;

        // when
        MusicalPostDTO postDTO = musicalBoardService.findPost(musicalBoardId);

        // then
        assertNotNull(postDTO, "조회된 게시글은 null이 아니다.");
    }
}