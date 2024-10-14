package com.threeping.mudium.musicalboard.service;

import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;

import java.util.List;

public interface MusicalBoardService {

    List<MusicalPostListDTO> findAllPost(Long musicalId);

    MusicalPostDTO findPost(Long musicalPostId);
}
