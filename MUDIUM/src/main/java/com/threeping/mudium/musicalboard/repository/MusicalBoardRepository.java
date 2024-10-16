package com.threeping.mudium.musicalboard.repository;

import com.threeping.mudium.musicalboard.aggregate.ActiveStatus;
import com.threeping.mudium.musicalboard.aggregate.MusicalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicalBoardRepository extends JpaRepository<MusicalPost, Long> {
    @Query("SELECT mp, (SELECT u.nickname FROM UserEntity u WHERE u.userId = mp.userId) as writerNickname " +
            "FROM musicalPost mp " +
            "WHERE mp.musicalId = :musicalId AND mp.activeStatus = :activeStatus")
    List<Object[]> findAllByMusicalIdAndActiveStatus(Long musicalId, ActiveStatus activeStatus);

    Optional<MusicalPost> findMusicalPostByMusicalPostIdAndUserIdAndActiveStatus(Long musicalPostId, Long userId, ActiveStatus activeStatus);

    Optional<MusicalPost> findMusicalPostByMusicalPostIdAndActiveStatus(Long musicalPostId, ActiveStatus activeStatus);
}
