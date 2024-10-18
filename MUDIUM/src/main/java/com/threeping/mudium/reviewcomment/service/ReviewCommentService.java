package com.threeping.mudium.reviewcomment.service;

import com.threeping.mudium.reviewcomment.dto.ReviewCommentDTO;

import java.util.List;

public interface ReviewCommentService {
    List<ReviewCommentDTO> findAllComment(Long reviewId);
}
