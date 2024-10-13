package com.threeping.mudium.customticket.repository;

import com.threeping.mudium.customticket.aggregate.entity.UserCustomTicketEntity;
import com.threeping.mudium.customticket.aggregate.entity.UserCustomTicketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCustomTicketRepository extends JpaRepository<UserCustomTicketEntity, UserCustomTicketId> {
}
