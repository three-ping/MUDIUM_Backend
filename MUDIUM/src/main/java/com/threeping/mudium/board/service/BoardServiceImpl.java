package com.threeping.mudium.board.service;

import com.threeping.mudium.board.aggregate.entity.ActiveStatus;
import com.threeping.mudium.board.aggregate.entity.Board;
import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import com.threeping.mudium.board.dto.RegistBoardDTO;
import com.threeping.mudium.board.repository.BoardRepository;
import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import com.threeping.mudium.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    BoardServiceImpl(BoardRepository boardRepository,
                     ModelMapper modelMapper, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Page<BoardListDTO> viewBoardList(Pageable pageable) {
        int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        int pageSize = pageable.getPageSize();
        Sort pageSort = Sort.by("createdAt").descending();
        Pageable boardPageable = PageRequest.of(pageNumber,pageSize,pageSort);

        Page<Board> boards = boardRepository.findAll(boardPageable);


        List<BoardListDTO> boardListDTOList = boards.stream()
                .map(board -> {
                    BoardListDTO boardListDTO = modelMapper.map(board, BoardListDTO.class);
                    boardListDTO.setNickname(board.getUser().getNickname());
                    return boardListDTO;
                })
                .collect(Collectors.toList());
        Page<BoardListDTO> boardListDTOs = new PageImpl<>(boardListDTOList,boardPageable,boards.getTotalElements());

        return boardListDTOs;
    }

    @Override
    @Transactional
    public BoardDetailDTO viewBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(()->new CommonException(ErrorCode.INVALID_BOARD_ID));
        BoardDetailDTO boardDetailDTO = modelMapper.map(board, BoardDetailDTO.class);
        boardDetailDTO.setNickname(board.getUser().getNickname());

        return boardDetailDTO;
    }

    @Override
    @Transactional
    public void createBoard(RegistBoardDTO registBoardDTO) {
        Board board = new Board();
        board.setTitle(registBoardDTO.getTitle());
        board.setContent(registBoardDTO.getContent());
        board.setActiveStatus(ActiveStatus.ACTIVE);
        board.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        board.setBoardLike(0L);
        board.setViewCount(0L);
        UserEntity user = userRepository.findById(registBoardDTO.getUserId()).orElseThrow(()->{
            return new CommonException(ErrorCode.NOT_FOUND_USER);
        });
        board.setUser(user);
        boardRepository.save(board);
    }
}
