package com.threeping.mudium.musical.repository;

import com.threeping.mudium.musical.aggregate.Musical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalRepository extends JpaRepository<Musical, Long> {
}
