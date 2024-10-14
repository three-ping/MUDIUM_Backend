package com.threeping.mudium.musicalboard.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;
import com.threeping.mudium.musicalboard.service.MusicalBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/musical-board")
@Slf4j
public class MusicalBoardController {

    private final MusicalBoardService musicalBoardService;

    @Autowired
    public MusicalBoardController(MusicalBoardService musicalBoardService) {
        this.musicalBoardService = musicalBoardService;
    }
//
//    @GetMapping("/{boardId}")
//    public ResponseDTO<?> findMusicalBoard(@PathVariable Long boardId) {
//        List<MusicalPostListDTO> postListDTOlist = musicalBoardService.findAllPost(boardId);
//
//        ResponseDTO<List<MusicalPostListDTO>> responseDTO = new ResponseDTO<>();
//        responseDTO.setData(postListDTOlist);
//        responseDTO.setHttpStatus(HttpStatus.OK);
//        responseDTO.setSuccess(true);
//        return responseDTO;
//    }

    // 글 조회는 글의 PK만 받도록한다.
    //단일 책임 원칙 (Single Responsibility Principle):
    //각 엔티티는 고유한 식별자를 가져야 한다. 게시글 PK가 있다면, 이것만으로도 unique한 게시글을 식별할 수 있어야 한다.
    //데이터베이스 설계:
    //일반적으로 게시글 테이블은 이미 뮤지컬 ID를 외래 키로 가지고 있다. 따라서 게시글 PK만으로도 해당 게시글이 어떤 뮤지컬에 속하는지 알 수 있다
    @GetMapping("/{musicalPostId}")
    public ResponseDTO<?> findMusicalPost(@PathVariable Long musicalPostId) {
        MusicalPostDTO postDTO = musicalBoardService.findPost(musicalPostId);

        ResponseDTO<MusicalPostDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(postDTO);
        responseDTO.setHttpStatus(HttpStatus.OK);
        responseDTO.setSuccess(true);
        return responseDTO;
    }
}
