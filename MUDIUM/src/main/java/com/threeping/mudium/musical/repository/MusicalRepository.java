package com.threeping.mudium.musical.repository;

import com.threeping.mudium.musical.aggregate.Musical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MusicalRepository extends JpaRepository<Musical, Long> {
    @Query(value = "SELECT * FROM TBL_MUSICAL_INFO m WHERE m.title = :title", nativeQuery = true)
    Optional<Musical> findMusicalByExactTitle(@Param("title") String title);
}
