package com.threeping.mudium.musical.controller;

import com.threeping.mudium.musical.service.MusicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/musical")
@Slf4j
public class MusicalController {

    private final MusicalService musicalService;

    @Autowired
    public MusicalController(MusicalService musicalService) {
        this.musicalService = musicalService;
    }


}
