package com.threeping.mudium.guidebook.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.guidebook.dto.EtiquetteRequestDTO;
import com.threeping.mudium.guidebook.entity.Etiquette;
import com.threeping.mudium.guidebook.service.ettiquette.EtiquetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/etiquette")
public class EtiquetteController {

    private final EtiquetteService etiquetteService;

    @Autowired
    public EtiquetteController ( EtiquetteService etiquetteService ) {
        this.etiquetteService = etiquetteService;
    }

    @PostMapping("/post")
    public ResponseDTO<?> createEtiquette ( @RequestBody EtiquetteRequestDTO etiquetteRequestDTO ){
        Etiquette createEtiquettes = etiquetteService.createEtiquette( etiquetteRequestDTO );
        return ResponseDTO.ok ( createEtiquettes );
    }
}
