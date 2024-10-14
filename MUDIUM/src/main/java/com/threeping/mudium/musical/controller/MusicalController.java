package com.threeping.mudium.musical.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.musical.dto.MusicalListDTO;
import com.threeping.mudium.musical.dto.MusicalTotalDTO;
import com.threeping.mudium.musical.service.MusicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musical")
@Slf4j
public class MusicalController {

    private final MusicalService musicalService;

    @Autowired
    public MusicalController(MusicalService musicalService) {
        this.musicalService = musicalService;
    }


    @GetMapping("")
    public ResponseDTO<?> findAllMusical(@RequestParam(required = false) String title, Pageable pageable) {
        Page<MusicalListDTO> musicalPage = musicalService.findByName(title, pageable);

        ResponseDTO<Page<MusicalListDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(true);
        responseDTO.setData(musicalPage);
        responseDTO.setHttpStatus(HttpStatus.OK);

        return responseDTO;
    }

    @GetMapping("/{musicalId}")
    public ResponseDTO<?> findMusical(@PathVariable("musicalId") Long musicalId) {
        MusicalTotalDTO totalDTO = musicalService.findMusicalDetail(musicalId);

        ResponseDTO<MusicalTotalDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(totalDTO);
        responseDTO.setHttpStatus(HttpStatus.OK);
        responseDTO.setSuccess(true);

        return responseDTO;
    }
}
