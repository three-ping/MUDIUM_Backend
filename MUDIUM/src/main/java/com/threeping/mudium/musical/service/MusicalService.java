package com.threeping.mudium.musical.service;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.dto.MusicalTotalDTO;


public interface MusicalService {
    MusicalTotalDTO findMusicalDetail(Long musicId);

    Musical findMusicalByMusicalId(Long musicalId);
}
