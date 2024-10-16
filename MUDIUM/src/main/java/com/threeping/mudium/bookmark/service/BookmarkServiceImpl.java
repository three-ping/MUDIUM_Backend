package com.threeping.mudium.bookmark.service;

import com.threeping.mudium.bookmark.dto.BookmarkResponseDTO;
import com.threeping.mudium.bookmark.entity.Bookmark;
import com.threeping.mudium.bookmark.entity.BookmarkPK;
import com.threeping.mudium.bookmark.repository.BookmarkRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkServiceImpl ( BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }


    @Override
    public Bookmark addBookmark (Long userId, Long musicalId ) {
        Bookmark bookmark = new Bookmark ();
        bookmark.setUserId ( userId);    // * userId 입력 강제성
        bookmark.setMusicalId ( musicalId);

        return bookmarkRepository.save (bookmark);
    }

    @Override
    public void deleteBookmark (Long userId, Long musicalId ) {
        BookmarkPK bookmarkPK = new BookmarkPK (userId, musicalId);
        bookmarkRepository.deleteById ( bookmarkPK );

    }

    @Override
    public List<BookmarkResponseDTO> findBookmarkByUserId ( Long userId ) {
        return bookmarkRepository.findAllByUserId(userId).stream()
                .map(bookmark -> new BookmarkResponseDTO(
                        bookmark.getUserId (),
                        bookmark.getMusicalId ()))
                .collect( Collectors.toList());
    }
}
