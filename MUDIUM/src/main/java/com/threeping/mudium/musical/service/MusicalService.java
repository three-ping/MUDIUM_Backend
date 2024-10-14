package com.threeping.mudium.musical.service;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.dto.MusicalListDTO;
import com.threeping.mudium.musical.dto.MusicalTotalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MusicalService {
    MusicalTotalDTO findMusicalDetail(Long musicId);

    Musical findMusicalByMusicalId(Long musicalId);

    Page<MusicalListDTO> findByName(String title,Pageable pageable);

}
