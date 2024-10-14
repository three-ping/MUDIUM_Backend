package com.threeping.mudium.secretreview.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.secretreview.dto.SecretReviewResponseDTO;
import com.threeping.mudium.secretreview.service.SecretReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/secretreview")
public class SecretReviewController {

    private final SecretReviewService secretReviewService;

    @Autowired
    public SecretReviewController(SecretReviewService secretReviewService) {
        this.secretReviewService = secretReviewService;
    }

    // 비밀리뷰 전체 조회
    @GetMapping("")
    private ResponseDTO<?> findSecretReviewByUserId(@RequestBody Long userId) {
        List<SecretReviewResponseDTO> secretReviewResponseDTO = secretReviewService.findSecretReviewByUserId(userId);

        return ResponseDTO.ok(secretReviewResponseDTO);
    }

    // 비밀리뷰 상세 조회
    @GetMapping("/{secretReviewId}")
    private ResponseDTO<?> findSecretReviewByUserIdAndSecretReviewId(@PathVariable Long secretReviewId,
                                                                     @RequestBody Long userId) {
        List<SecretReviewResponseDTO> secretReviewResponseDTO =
                secretReviewService.findSecretReviewByUserIdAndSecretReviewId(userId, secretReviewId);

        return ResponseDTO.ok(secretReviewResponseDTO);
    }
}
