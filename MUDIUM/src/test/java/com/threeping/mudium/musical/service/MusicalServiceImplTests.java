package com.threeping.mudium.musical.service;

import com.threeping.mudium.musical.dto.MusicalTotalDTO;
import com.threeping.mudium.performance.dto.PerformanceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicalServiceImplTests {

    private final MusicalService musicalService;

    @Autowired
    public MusicalServiceImplTests(MusicalService musicalService) {
        this.musicalService = musicalService;
    }

    @DisplayName("뮤지컬 번호로 뮤지컬 상세 정보를 조회한다.")
    @Test
    void musicalDetailViewTest() {
        // given
        Long musicId = 1L;

        // when
        MusicalTotalDTO musicalDetail = musicalService.findMusicalDetail(musicId);
        List<PerformanceDTO> list = musicalDetail.getPerformanceList();

        // then
        assertNotNull(musicalDetail, "조회된 뮤지컬 상세 정보는 null이 아니다.");
        assertFalse(list.isEmpty(), "조회된 뮤지컬의 공연 정보는 비어있지 않다.");
    }
}