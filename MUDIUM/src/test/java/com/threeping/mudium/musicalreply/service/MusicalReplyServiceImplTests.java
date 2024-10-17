package com.threeping.mudium.musicalreply.service;

import com.threeping.mudium.mucialcomment.dto.MusicalCommentDTO;
import com.threeping.mudium.mucialcomment.service.MusicalCommentService;
import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;
import com.threeping.mudium.musicalboard.service.MusicalBoardService;
import com.threeping.mudium.musicalreply.dto.MusicalReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MusicalReplyServiceImplTests {

    private final MusicalReplyService musicalReplyService;
    private final MusicalCommentService musicalCommentService;
    private final MusicalBoardService musicalBoardService;

    @Autowired
    public MusicalReplyServiceImplTests(MusicalReplyService musicalReplyService,
                                        MusicalCommentService musicalCommentService,
                                        MusicalBoardService musicalBoardService) {
        this.musicalReplyService = musicalReplyService;
        this.musicalCommentService = musicalCommentService;
        this.musicalBoardService = musicalBoardService;
    }

    @DisplayName("특정 댓글의 모든 대댓글을 조회한다.")
    @Test
    void searchReplies() {
        // Given
        Long commentId = 51L;

        // When
        List<MusicalReplyDTO> list = musicalReplyService.findAllReply(commentId);
        // Then
        assertNotNull(list, "조회된 대댓글 list는 null이 아니다.");
        assertTrue(list.get(0).getCommentId() == commentId, "조회된 대댓글의 댓글 Id 일치 확인");
    }

    @DisplayName("특정 댓글에 대댓글을 작성한다.")
    @Test
    void createReplies() {
        // Given
        Long userId = 1L;
        Long musicalId = 1L;
        MusicalPostDTO postDTO = new MusicalPostDTO();
        postDTO.setTitle("제목");
        postDTO.setContent("내용");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        musicalBoardService.createPost(musicalId, userId, postDTO);

        Page<MusicalPostListDTO> list = musicalBoardService.findAllPost(musicalId, pageable);
        int size = list.getContent().size();
        Long postId = list.getContent().get(size - 1).getPostId();

        MusicalCommentDTO commentDTO = new MusicalCommentDTO();
        commentDTO.setContent("댓글 댓글 댓글");
        commentDTO.setPostId(postId);
        musicalCommentService.createComment(userId, commentDTO);

        MusicalCommentDTO savedComment = musicalCommentService.findComment(postId).get(0);
        Long commentId = savedComment.getCommentId();


    }
}