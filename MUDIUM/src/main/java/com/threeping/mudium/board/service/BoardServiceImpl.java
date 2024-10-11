package com.threeping.mudium.board.service;

import com.threeping.mudium.board.aggregate.entity.Board;
import com.threeping.mudium.board.dto.BoardDetailDTO;
import com.threeping.mudium.board.dto.BoardListDTO;
import com.threeping.mudium.board.repository.BoardRepository;
import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    BoardServiceImpl(BoardRepository boardRepository,
                     ModelMapper modelMapper){
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Page<BoardListDTO> viewBoardList(Pageable pageable) {

        int pageNumber = pageable.getPageNumber()<=1 ? 0 : pageable.getPageNumber() - 1;
        int pageSize = pageable.getPageSize();
        Sort pageSort = Sort.by("createdAt").descending();
        Pageable boardPageable = PageRequest.of(pageNumber,pageSize,pageSort);

        Page<Board> boards = boardRepository.findAll(boardPageable);

        if (boards.getTotalPages()<pageNumber) {
            Pageable maxPageable = PageRequest.of(boards.getTotalPages()-1,pageSize,pageSort);
            boards = boardRepository.findAll(maxPageable);
        }

        List<BoardListDTO> boardListDTOList = boards.stream()
                .map(board -> {
                    BoardListDTO boardListDTO = modelMapper.map(board, BoardListDTO.class);
                    boardListDTO.setNickname(board.getUser().getNickname());
                    return boardListDTO;
                })
                .collect(Collectors.toList());
        Page<BoardListDTO> boardListDTOs = new PageImpl<>(boardListDTOList,pageable,boards.getTotalElements());

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
}
