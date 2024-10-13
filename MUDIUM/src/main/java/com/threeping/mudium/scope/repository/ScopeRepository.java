package com.threeping.mudium.scope.repository;

import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
import com.threeping.mudium.scope.aggregate.entity.ScopeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeRepository extends JpaRepository<ScopeEntity, ScopeId> {

    void deleteScopeByMusicalIdAndUserId(Long musicalId, Long userId);
}
