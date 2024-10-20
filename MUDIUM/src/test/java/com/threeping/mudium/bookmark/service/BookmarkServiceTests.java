package com.threeping.mudium.bookmark.service;

import com.threeping.mudium.bookmark.entity.Bookmark;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Transactional
class BookmarkServiceTests {

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    @DisplayName("북마크 추가 테스트")
    void testAddBookMark(){

        // given
        Long userId = 1L;
        Long musicalId = 1L;

        // when
        Bookmark addedBookmark = bookmarkService.addBookmark(userId, musicalId);

        // then
        assertNotNull(addedBookmark);
        log.info("addedBookmark: {}", addedBookmark);
    }

}