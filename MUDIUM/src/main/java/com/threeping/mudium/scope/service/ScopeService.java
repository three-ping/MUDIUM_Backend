package com.threeping.mudium.scope.service;

import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;

public interface ScopeService {

//    ScopeEntity createScope(ScopeEntity scopeEntity);
//
//    ScopeEntity updateScope(ScopeEntity scopeEntity);
    ScopeEntity createOrUpdateScope(ScopeEntity scopeEntity);

    void deleteScope(Long musicalId, Long userId);
}
