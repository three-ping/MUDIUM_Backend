package com.threeping.mudium.user.repository;

import com.threeping.mudium.user.aggregate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserIdentifier(String userIdentifier);
}
