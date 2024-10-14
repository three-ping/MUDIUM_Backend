package com.threeping.mudium.musicalboard.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.service.MusicalService;
import com.threeping.mudium.musicalboard.aggregate.ActiveStatus;
import com.threeping.mudium.musicalboard.aggregate.MusicalPost;
import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;
import com.threeping.mudium.musicalboard.repository.MusicalBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MusicalBoardServiceImpl implements MusicalBoardService {

    private final MusicalBoardRepository musicalBoardRepository;
    private final MusicalService musicalService;

    @Autowired
    public MusicalBoardServiceImpl(MusicalBoardRepository musicalBoardRepository, MusicalService musicalService) {
        this.musicalBoardRepository = musicalBoardRepository;
        this.musicalService = musicalService;
    }

    @Override
    public List<MusicalPostListDTO> findAllPost(Long musicalId) {
        Musical musical = musicalService.findMusicalByMusicalId(musicalId);
        List<MusicalPost> postList = musicalBoardRepository.findAllByMusicalAndActiveStatus(musical, ActiveStatus.ACTIVE);

        List<MusicalPostListDTO> postDTOList = postList.stream()
                .map(musicalPost -> {
                    MusicalPostListDTO postDTO = new MusicalPostListDTO();
                    postDTO.setPostId(musicalPost.getMusicalPostId());
                    postDTO.setTitle(musicalPost.getTitle());
                    postDTO.setLike(musicalPost.getLike());
                    postDTO.setViewCount(viewConverter(musicalPost.getViewCount()));
                    postDTO.setCreatedAt(timeConverter(musicalPost.getCreatedAt()));
                    postDTO.setWriter(musicalPost.getUserEntity().getNickname());
                    return postDTO;
                }).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public MusicalPostDTO findPost(Long musicalPostId) {
        MusicalPost musicalPost = musicalBoardRepository.findMusicalPostsByMusicalPostId(musicalPostId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_BOARD_ID));

        if(musicalPost.getActiveStatus() == ActiveStatus.INACTIVE) {
            throw new CommonException(ErrorCode.NOT_FOUND_MUSICAL_BOARD);
        }

        MusicalPostDTO postDTO = new MusicalPostDTO();
        postDTO.setTitle(musicalPost.getTitle());
        postDTO.setContent(musicalPost.getContent());
        postDTO.setLike(musicalPost.getLike());
        postDTO.setCreatedAt(detailTimeConverter(musicalPost.getCreatedAt()));
        postDTO.setUpdatedAt(detailTimeConverter(musicalPost.getUpdatedAt()));
        postDTO.setNickname(musicalPost.getUserEntity().getNickname());
        postDTO.setViewCount(musicalPost.getViewCount());

        return postDTO;
    }

    private String viewConverter(Long viewCount) {
        if(viewCount > 10000) {
            return viewCount / 10000 + "만";
        }
        return viewCount + "";
    }

    private String timeConverter(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private String detailTimeConverter(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return sdf.format(date);
    }
}
