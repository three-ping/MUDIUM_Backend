package com.threeping.mudium.musicalreply.service;

import com.threeping.mudium.musicalreply.dto.MusicalReplyDTO;

import java.util.List;

public interface MusicalReplyService {
    List<MusicalReplyDTO> findAllReply(Long commentId);
}
