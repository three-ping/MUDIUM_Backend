package com.threeping.mudium.musicalboard.repository;

import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musicalboard.aggregate.ActiveStatus;
import com.threeping.mudium.musicalboard.aggregate.MusicalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicalBoardRepository extends JpaRepository<MusicalPost, Long> {

    List<MusicalPost> findAllByMusicalAndActiveStatus(Musical musical, ActiveStatus activeStatus);

    Optional<MusicalPost> findMusicalPostsByMusicalPostId(Long musicalPostId);
}
