    package com.threeping.mudium.scope.service;

    import com.threeping.mudium.common.exception.CommonException;
    import com.threeping.mudium.common.exception.ErrorCode;
    import com.threeping.mudium.scope.aggregate.entity.ScopeEntity;
    import com.threeping.mudium.scope.aggregate.entity.ScopeId;
    import com.threeping.mudium.scope.dto.ScopeDTO;
    import com.threeping.mudium.scope.repository.ScopeRepository;
    import com.threeping.mudium.scope.vo.ScopeVO;
    import com.threeping.mudium.user.aggregate.entity.UserEntity;
    import com.threeping.mudium.user.service.UserService;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.sql.Timestamp;
    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    @Service
    public class ScopeServiceImpl implements ScopeService {

        private final ScopeRepository scopeRepository;
        private final UserService userService;

        public ScopeServiceImpl(ScopeRepository scopeRepository, UserService userService) {
            this.scopeRepository = scopeRepository;
            this.userService = userService;
        }

        @Override
        @Transactional
        public List<ScopeDTO> findAllScopesByMusicalId(Long musicalId) {
            List<ScopeEntity> entityList = scopeRepository.findAllScopeByMusicalId(musicalId);

            // 특정 뮤지컬에 대한 모든 별점을 보며울 때 줄 거 = 뮤지컬 id, user 정보(닉네임), 별점
            List<ScopeDTO> dtoList = entityList.stream()
                    .map(scopeEntity -> {
                        ScopeDTO dto = new ScopeDTO();
                        dto.setMusicalId(scopeEntity.getMusicalId());
                        dto.setScope(scopeEntity.getScope());
                        dto.setNickName(scopeEntity.getUserNickname());
                        return dto;
                    }).collect(Collectors.toList());

            return dtoList;
        }

        @Override
        @Transactional(readOnly = true)
        public Map<Long, String> calculateAverageScopeBatch(List<Long> musicalIds) {
            List<Object[]> results = scopeRepository.findAverageScopesByMusicalIds(musicalIds);
            Map<Long, String> averageScopes = new HashMap<>();

            for (Object[] r : results) {
                Long musicalId = (Long) r[0];
                Double averageScope = (Double) r[1];
                averageScopes.put(musicalId, averageConverter(averageScope));
            }

            // 결과에 포함되지 않은 뮤지컬은 평균 별점을 모두 0점으로 세팅
            for (Long musicalId : musicalIds) {
                averageScopes.putIfAbsent(musicalId, "0점");
            }

            return averageScopes;
        }

        private String averageConverter(Double average) {

            double roundedScope = Math.ceil(average * 100) / 100.0;

            return String.format("%.1f", roundedScope);
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
        public ScopeEntity createOrUpdateScope(Long musicalId, Long userId, ScopeVO scopeVO) {
            ScopeId scopeId = new ScopeId(musicalId, userId);
//            UserEntity user = userService.getUserByUserId(userId);
            // 기존 별점 존재 여부 확인
            ScopeEntity existingScope = scopeRepository.findById(scopeId).orElse(null);

            if (existingScope != null) {
                // 별점이 이미 존재하는 경우 -> 수정
                existingScope.setScope(scopeVO.getScope());
                existingScope.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                return scopeRepository.save(existingScope);
            } else {
                // 별점이 없는 경우 -> 새로 추가
                ScopeEntity scopeEntity = new ScopeEntity();
                scopeEntity.setMusicalId(musicalId);
                scopeEntity.setScope(scopeVO.getScope());
                scopeEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                scopeEntity.setUserId(userId);
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
