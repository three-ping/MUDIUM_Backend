package com.threeping.mudium.musicalreply.service;

import com.threeping.mudium.musicalreply.aggregate.ActiveStatus;
import com.threeping.mudium.musicalreply.aggregate.MusicalReply;
import com.threeping.mudium.musicalreply.dto.MusicalReplyDTO;
import com.threeping.mudium.musicalreply.repository.MusicalReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MusicalReplyServiceImpl implements MusicalReplyService {

    private final MusicalReplyRepository musicalReplyRepository;

    @Autowired
    public MusicalReplyServiceImpl(MusicalReplyRepository musicalReplyRepository) {
        this.musicalReplyRepository = musicalReplyRepository;
    }

    @Override
    public List<MusicalReplyDTO> findAllReply(Long commentId) {
        List<Object[]> results = musicalReplyRepository.findAllByCommentIdOrderByCreatedAtDesc(commentId);

        List<MusicalReplyDTO> repliesDTO = results.stream()
                .map(result -> {
                    MusicalReply reply = (MusicalReply) result[0];
                    String nickname = (String) result[1];

                    MusicalReplyDTO replyDTO = new MusicalReplyDTO();
                    replyDTO.setCommentId(reply.getCommentId());
                    replyDTO.setMusicalReplyId(reply.getMusicalReplyId());
                    if(reply.getActiveStatus().equals(ActiveStatus.INACTIVE)) {
                        replyDTO.setContent("삭제된 댓글입니다.");
                        return replyDTO;
                    }
                    replyDTO.setContent(reply.getContent());
                    replyDTO.setCreatedAt(timeConverter(reply.getCreatedAt()));
                    replyDTO.setUpdatedAt(timeConverter(reply.getUpdatedAt()));
                    replyDTO.setNickName(nickname);
                    return replyDTO;
                }).collect(Collectors.toList());


        return repliesDTO;
    }

    private String timeConverter(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
