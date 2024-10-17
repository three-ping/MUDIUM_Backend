package com.threeping.mudium.notice.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.notice.aggregate.enumerate.SearchType;
import com.threeping.mudium.notice.aggregate.entity.Notice;
import com.threeping.mudium.notice.dto.CreateNoticeDTO;
import com.threeping.mudium.notice.dto.NoticeDetailDTO;
import com.threeping.mudium.notice.dto.NoticeListDTO;
import com.threeping.mudium.notice.repository.NoticeRepository;
import com.threeping.mudium.user.aggregate.entity.UserRole;
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

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    NoticeServiceImpl(NoticeRepository noticeRepository,
                     ModelMapper modelMapper,
                      UserRepository userRepository){
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<NoticeListDTO> viewBoardList(Pageable pageable) {
        int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        int pageSize = pageable.getPageSize();
        Sort pageSort = Sort.by("createdAt").descending();
        Pageable noticePageable = PageRequest.of(pageNumber,pageSize,pageSort);

        Page<Notice> notices = noticeRepository.findAll(noticePageable);

        List<NoticeListDTO> boardListDTOList = notices.stream()
                .map(notice -> {
                    NoticeListDTO noticeListDTO = modelMapper.map(notice, NoticeListDTO.class);
                    return noticeListDTO;
                })
                .collect(Collectors.toList());
        Page<NoticeListDTO> noticeListDTOs = new PageImpl<>(boardListDTOList,noticePageable,notices.getTotalElements());

        return noticeListDTOs;
    }

    @Override
    public Page<NoticeListDTO> viewSearchedNoticeList(Pageable pageable, SearchType searchType, String searchQuery) {
        int pageNumber = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        int pageSize = pageable.getPageSize();
        Sort pageSort = Sort.by("createdAt").descending();
        Pageable noticePageable = PageRequest.of(pageNumber,pageSize,pageSort);
        if (searchType.equals(SearchType.TITLE)) {
            return searchNoticeByTitle(searchQuery,noticePageable);
        } else if (searchType.equals(SearchType.CONTENT)) {
            return searchNoticeByContent(searchQuery,noticePageable);
        }
        return null;
    }

    @Override
    public NoticeDetailDTO viewNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow
                (()->new CommonException(ErrorCode.INVALID_NOTICE_ID));
        NoticeDetailDTO noticeDetailDTO = modelMapper.map(notice,NoticeDetailDTO.class);
        return noticeDetailDTO;
    }

    @Override
    @Transactional
    public void createNotice(CreateNoticeDTO createNoticeDTO) {
        Long userId = createNoticeDTO.getUserId();
        UserRole userRole = userRepository.findByUserId(userId).getUserRole();
        if(userRole.equals(UserRole.ROLE_MEMBER)) throw new CommonException(ErrorCode.INVALID_USER_ROLE);

        createNoticeDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Notice notice = modelMapper.map(createNoticeDTO,Notice.class);
        notice.setViewCount(0L);
        noticeRepository.save(notice);
    }

    public Page<NoticeListDTO> searchNoticeByTitle(String title, Pageable pageable) {
        return noticeRepository.findByTitleContaining(title, pageable).map(this::convertToDTO);
    }

    public Page<NoticeListDTO> searchNoticeByContent(String content, Pageable pageable) {
        return noticeRepository.findByContentContaining(content, pageable).map(this::convertToDTO);
    }

    private NoticeListDTO convertToDTO(Notice notice) {
        NoticeListDTO noticeListDTO = new NoticeListDTO();
        noticeListDTO.setTitle(notice.getTitle());
        noticeListDTO.setId(notice.getNoticeId());
        noticeListDTO.setCreatedAt(notice.getCreatedAt());
        noticeListDTO.setUserId(notice.getUser().getUserId());
        return noticeListDTO;
    }

}
