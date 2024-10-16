package com.threeping.mudium.musicalboard.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.service.MusicalService;
import com.threeping.mudium.musicalboard.aggregate.ActiveStatus;
import com.threeping.mudium.musicalboard.aggregate.MusicalPost;
import com.threeping.mudium.musicalboard.dto.MusicalPostDTO;
import com.threeping.mudium.musicalboard.dto.MusicalPostListDTO;
import com.threeping.mudium.musicalboard.repository.MusicalBoardRepository;
import com.threeping.mudium.user.aggregate.dto.UserDTO;
import com.threeping.mudium.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MusicalBoardServiceImpl implements MusicalBoardService {

    private final MusicalBoardRepository musicalBoardRepository;
    private final MusicalService musicalService;
    private final UserService userService;

    @Autowired
    public MusicalBoardServiceImpl(MusicalBoardRepository musicalBoardRepository,
                                   MusicalService musicalService,
                                   UserService userService) {
        this.musicalBoardRepository = musicalBoardRepository;
        this.musicalService = musicalService;
        this.userService = userService;
    }

    @Override
    public List<MusicalPostListDTO> findAllPost(Long musicalId) {
        List<Object[]> results = musicalBoardRepository.findAllByMusicalIdAndActiveStatus(musicalId, ActiveStatus.ACTIVE);

        List<MusicalPostListDTO> postDTOList = results.stream()
                .map(result -> {
                    MusicalPost post = (MusicalPost) result[0];
                    String nickName = (String) result[1];

                    MusicalPostListDTO postDTO = new MusicalPostListDTO();
                    postDTO.setTitle(post.getTitle());
                    postDTO.setViewCount(viewConverter(post.getViewCount()));
                    postDTO.setPostId(post.getMusicalPostId());
                    postDTO.setLikeCount(post.getLikeCount());
                    postDTO.setCreatedAt(timeConverter(post.getCreatedAt()));
                    postDTO.setWriter(nickName);
                    return postDTO;
                }).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public MusicalPostDTO findPost(Long musicalPostId) {
        MusicalPost musicalPost = musicalBoardRepository.findMusicalPostByMusicalPostIdAndActiveStatus(musicalPostId, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_BOARD_ID));
        UserDTO user = userService.findByUserId(musicalPost.getUserId());


        MusicalPostDTO postDTO = new MusicalPostDTO();
        postDTO.setTitle(musicalPost.getTitle());
        postDTO.setContent(musicalPost.getContent());
        postDTO.setLike(musicalPost.getLikeCount());
        postDTO.setCreatedAt(detailTimeConverter(musicalPost.getCreatedAt()));
        postDTO.setUpdatedAt(detailTimeConverter(musicalPost.getUpdatedAt()));
        postDTO.setNickname(user.getNickname());
        postDTO.setViewCount(musicalPost.getViewCount());

        return postDTO;
    }

    @Override
    public void createPost(Long musicalId, Long userId, MusicalPostDTO postDTO) {
        MusicalPost musicalPost = new MusicalPost();
        musicalPost.setTitle(postDTO.getTitle());
        musicalPost.setContent(postDTO.getContent());
        musicalPost.setMusicalId(musicalId);
        musicalPost.setUserId(userId);
        musicalPost.setCreatedAt(Timestamp.from(Instant.now()));
        musicalPost.setViewCount(Long.valueOf(1));
        musicalPost.setLikeCount(Long.valueOf(0));

        musicalBoardRepository.save(musicalPost);
    }

    @Override
    public void updatePost(Long musicalPostId, Long userId, MusicalPostDTO postDTO) {
        MusicalPost musicalPost = musicalBoardRepository.
                findMusicalPostByMusicalPostIdAndUserIdAndActiveStatus(musicalPostId, userId, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_BOARD_ID));
        if(!postDTO.getTitle().isEmpty()) {
            postDTO.setTitle(musicalPost.getTitle());
        }
        if(!postDTO.getContent().isEmpty()) {
            musicalPost.setContent(postDTO.getContent());
        }
        musicalPost.setUpdatedAt(Timestamp.from(Instant.now()));

        musicalBoardRepository.save(musicalPost);
    }

    @Override
    public void deletePost(Long musicalPostId, Long userId) {
        MusicalPost musicalPost = musicalBoardRepository.
                findMusicalPostByMusicalPostIdAndUserIdAndActiveStatus(musicalPostId, userId, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_MUSICAL_BOARD_ID));

        musicalPost.softDelete();
        musicalBoardRepository.save(musicalPost);
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
