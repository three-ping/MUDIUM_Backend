package com.threeping.mudium.notice.repository;

import com.threeping.mudium.notice.aggregate.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
