package com.threeping.mudium.customticket.repository;

import com.threeping.mudium.customticket.aggregate.entity.CustomTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTicketRepository extends JpaRepository<CustomTicketEntity, Long> {
}
