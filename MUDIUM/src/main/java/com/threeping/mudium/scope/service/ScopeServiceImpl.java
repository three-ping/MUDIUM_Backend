    package com.threeping.mudium.scope.service;

    import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
    import com.threeping.mudium.scope.aggregate.entity.ScopeId;
    import com.threeping.mudium.scope.repository.ScopeRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.sql.Timestamp;
    import java.time.LocalDateTime;

    @Service
    public class ScopeServiceImpl implements ScopeService {

        private final ScopeRepository scopeRepository;

        public ScopeServiceImpl(ScopeRepository scopeRepository) {
            this.scopeRepository = scopeRepository;
        }

//        // 별점 추가
//        @Override
//        public ScopeEntity createScope(ScopeEntity scopeEntity) {
//            scopeEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
//            return scopeRepository.save(scopeEntity);
//        }
//
//        // 별점 수정
//        @Override
//        public ScopeEntity updateScope(ScopeEntity scopeEntity) {
//            scopeEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//            return scopeRepository.save(scopeEntity);
//        }

        // 별점 추가/수정 로직
        @Override
        public ScopeEntity createOrUpdateScope(ScopeEntity scopeEntity) {
            ScopeId scopeId = new ScopeId(scopeEntity.getMusicalId(), scopeEntity.getUserId());

            // 기존 별점 존재 여부 확인
            ScopeEntity existingScope = scopeRepository.findById(scopeId).orElse(null);

            if (existingScope != null) {
                // 별점이 이미 존재하는 경우 -> 수정
                existingScope.setScope(scopeEntity.getScope());
                existingScope.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                return scopeRepository.save(existingScope);
            } else {
                // 별점이 없는 경우 -> 새로 추가
                scopeEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                return scopeRepository.save(scopeEntity);
            }

        // 별점 삭제(일단 뮤지컬 id랑 회원 id로 삭제)
//        @Override
//        @Transactional
//        public void deleteScope(Long musicalx   Id, Long userId) {
//            scopeRepository.deleteScopeByMusicalIdAndUserId(musicalId, userId);
//        }
        }
        @Override
        @Transactional public void deleteScope(Long musicalId, Long userId) {
                scopeRepository.deleteScopeByMusicalIdAndUserId(musicalId, userId);
            }

    }
