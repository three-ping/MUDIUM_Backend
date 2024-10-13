package com.threeping.mudium.scope.controller;

import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
import com.threeping.mudium.scope.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/scope")
public class ScopeController {

    private final ScopeService scopeService;

    @Autowired
    public ScopeController(ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @PostMapping("/create/{userId}/{musicalId}")
    public ResponseEntity<ScopeEntity> createScope(@PathVariable("musicalId") Long musicalId,
                                                   @PathVariable("userId") Long userId,
                                                   @RequestBody ScopeEntity scopeEntity) {
        scopeEntity.setMusicalId(musicalId);  // musicalId 설정
        scopeEntity.setUserId(userId);        // userId 설정
        ScopeEntity createdRating = scopeService.createOrUpdateScope(scopeEntity);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    // 별점 수정 (PathVariable 사용)
    @PutMapping("/update/{userId}/{musicalId}")
    public ResponseEntity<ScopeEntity> updateScope(@PathVariable("musicalId") Long musicalId,
                                                   @PathVariable("userId") Long userId,
                                                   @RequestBody ScopeEntity scopeEntity) {
        scopeEntity.setMusicalId(musicalId);  // musicalId 설정
        scopeEntity.setUserId(userId);        // userId 설정
        ScopeEntity updatedRating = scopeService.createOrUpdateScope(scopeEntity);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }


    // 별점 삭제 (PathVariable 사용)
    @DeleteMapping("/delete/{userId}/{musicalId}")
    public ResponseEntity<Void> deleteScope(@PathVariable("userId") Long userId,
                                            @PathVariable("musicalId") Long musicalId) {
        scopeService.deleteScope(musicalId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
