package com.threeping.mudium.musical.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.musical.dto.MusicalTotalDTO;
import com.threeping.mudium.musical.service.MusicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/musicals")
@Slf4j
public class MusicalController {

    private final MusicalService musicalService;

    @Autowired
    public MusicalController(MusicalService musicalService) {
        this.musicalService = musicalService;
    }


    @GetMapping("/{musicalId}")
    public ResponseDTO<?> findMusical(@PathVariable Long musicalId) {
        MusicalTotalDTO totalDTO = musicalService.findMusicalDetail(musicalId);

        ResponseDTO<MusicalTotalDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(totalDTO);
        responseDTO.setHttpStatus(HttpStatus.OK);
        responseDTO.setSuccess(true);
        return responseDTO;
    }
}
