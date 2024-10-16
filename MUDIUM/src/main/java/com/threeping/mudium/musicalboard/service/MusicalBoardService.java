package com.threeping.mudium.musicalboard.service;

import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;

import java.util.List;

public interface MusicalBoardService {

    List<MusicalPostListDTO> findAllPost(Long musicalId);

    MusicalPostDTO findPost(Long musicalPostId);

    void createPost(Long musicalId, Long userId, MusicalPostDTO postDTO);

    void updatePost(Long musicalPostId, Long userId, MusicalPostDTO postDTO);

    void deletePost(Long musicalPostId, Long userId);
}
