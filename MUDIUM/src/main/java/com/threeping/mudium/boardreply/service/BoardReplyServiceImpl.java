package com.threeping.mudium.boardreply.service;

import com.threeping.mudium.board.aggregate.enumerate.ActiveStatus;
import com.threeping.mudium.boardreply.aggregate.entity.BoardReply;
import com.threeping.mudium.boardreply.dto.BoardReplyDTO;
import com.threeping.mudium.boardreply.repository.BoardReplyRepository;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
public class BoardReplyServiceImpl implements BoardReplyService{

    private final BoardReplyRepository boardReplyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    BoardReplyServiceImpl(BoardReplyRepository boardReplyRepository, ModelMapper modelMapper){
        this.boardReplyRepository = boardReplyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BoardReplyDTO> viewBoardReply(Long boardCommentId) {
        List<BoardReply> boardReplyList = boardReplyRepository.findByBoardCommentId(boardCommentId);
        List<BoardReplyDTO> boardReplyDTOS = boardReplyList.stream()
                .map(boardReply -> modelMapper.map(boardReply,BoardReplyDTO.class)).toList();

        return boardReplyDTOS;
    }

    @Override
    public void createBoardReply(BoardReplyDTO boardReplyDTO) {
        BoardReply boardReply = new BoardReply();
        UserEntity user = new UserEntity();
        user.setUserId(boardReplyDTO.getUserId());
        boardReply.setBoardCommentId(boardReplyDTO.getBoardCommentId());
        boardReply.setContent(boardReplyDTO.getContent());
        boardReply.setUser(user);
        boardReply.setActiveStatus(ActiveStatus.ACTIVE);
        boardReply.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        boardReplyRepository.save(boardReply);
    }
}
