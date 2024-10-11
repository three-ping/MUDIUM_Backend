package com.threeping.mudium.guidebook.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.guidebook.dto.request.RecommendedRequestDTO;
import com.threeping.mudium.guidebook.entity.RecommendedMusical;
import com.threeping.mudium.guidebook.service.RecommendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommended-musical")
public class RecommendedController {

    private final RecommendedService recommendedService;

    @Autowired
    public RecommendedController(RecommendedService recommendedService) {
        this.recommendedService = recommendedService;
    }

    @PostMapping("/")
    public ResponseDTO<?> createRecommended(@RequestBody RecommendedRequestDTO recommendedRequestDTO){
        RecommendedMusical createRecommend = recommendedService.createRecommended(recommendedRequestDTO);
        return ResponseDTO.ok(createRecommend);
    }

//    @PutMapping("/update/{recommendedId}")
}
