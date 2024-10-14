package com.threeping.mudium.scope.repository;

import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
import com.threeping.mudium.scope.aggregate.entity.ScopeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<ScopeEntity, ScopeId> {

    void deleteScopeByMusicalIdAndUserId(Long musicalId, Long userId);

    List<ScopeEntity> findAllScopeByMusicalId(Long musicalId);

    @Query("SELECT s.musicalId, COALESCE(AVG(s.scope), 0.0) FROM ScopeEntity s WHERE s.musicalId IN :musicalIds GROUP BY s.musicalId")
    List<Object[]> findAverageScopesByMusicalIds(@Param("musicalIds") List<Long> musicalIds);

}
