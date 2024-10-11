package com.threeping.mudium.musical.service;

import com.threeping.mudium.musical.dto.MusicalTotalDTO;

public interface MusicalService {
    MusicalTotalDTO findMusicalDetail(Long musicId);
}
