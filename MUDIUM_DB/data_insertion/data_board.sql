INSERT INTO `TBL_BOARD` (`title`, `content`, `created_at`, `view_count`, `board_like`, `active_status`, `user_id`)
VALUES 
('뮤지컬 <레미제라블> 리뷰', '레미제라블의 감동적인 서사와 음악은 언제 들어도 최고입니다!', DATE_SUB(NOW(), INTERVAL 30 DAY), 3, 0, 'ACTIVE', 1),
('공연 추천: <위키드>', '위키드의 화려한 무대와 배우들의 뛰어난 연기를 놓치지 마세요!', DATE_SUB(NOW(), INTERVAL 29 DAY), 2, 0, 'ACTIVE', 2),
('뮤지컬 <지킬 앤 하이드> 감상', '지킬과 하이드의 내면 갈등을 표현한 연기가 정말 인상적이었어요.', DATE_SUB(NOW(), INTERVAL 28 DAY), 5, 0, 'ACTIVE', 3),
('다음달 개봉 예정 뮤지컬 <캣츠>', '캣츠의 무대 디자인과 배우들의 퍼포먼스가 정말 기대됩니다.', DATE_SUB(NOW(), INTERVAL 27 DAY), 1, 0, 'ACTIVE', 4),
('뮤지컬 <노트르담 드 파리>의 매력', '노트르담 드 파리의 강렬한 음악과 독특한 무대 디자인에 빠졌습니다.', DATE_SUB(NOW(), INTERVAL 26 DAY), 4, 0, 'ACTIVE', 5),

('공연 후일담: <오페라의 유령>', '오페라의 유령에서 팬텀의 슬픔을 느낄 수 있는 연기가 대단했어요.', DATE_SUB(NOW(), INTERVAL 25 DAY), 3, 0, 'ACTIVE', 7),
('<맨 오브 라만차>를 보고 느낀 점', '돈키호테의 꿈과 희망을 노래한 뮤지컬, 다시 보고 싶네요.', DATE_SUB(NOW(), INTERVAL 24 DAY), 2, 0, 'ACTIVE', 1),
('뮤지컬 <시카고>의 흥미로운 스토리', '시카고는 재즈와 범죄가 어우러진 멋진 공연이었어요.', DATE_SUB(NOW(), INTERVAL 23 DAY), 1, 0, 'ACTIVE', 2),
('<빌리 엘리어트>의 성장 이야기', '빌리 엘리어트에서 느낀 감동은 정말 잊을 수 없어요.', DATE_SUB(NOW(), INTERVAL 22 DAY), 4, 0, 'ACTIVE', 3),
('뮤지컬 <렌트> 감상 후기', '렌트의 젊음과 자유를 표현한 메시지가 인상적이었어요.', DATE_SUB(NOW(), INTERVAL 21 DAY), 5, 0, 'ACTIVE', 4),

('<알라딘>의 무대 연출', '알라딘의 마법같은 무대 전환과 배우들의 연기가 인상 깊었습니다.', DATE_SUB(NOW(), INTERVAL 20 DAY), 2, 0, 'ACTIVE', 5),
('뮤지컬 <해밀턴>의 역사적 재해석', '해밀턴의 속도감 있는 연출과 흥겨운 음악이 매력적이었습니다.', DATE_SUB(NOW(), INTERVAL 19 DAY), 3, 0, 'ACTIVE', 7),
('<스쿨 오브 락>의 에너지를 느끼다', '스쿨 오브 락의 배우들이 보여준 열정적인 연기와 음악에 반했어요.', DATE_SUB(NOW(), INTERVAL 18 DAY), 1, 0, 'ACTIVE', 1),
('뮤지컬 <모차르트>의 음악적 아름다움', '모차르트의 생애를 그린 감동적인 음악과 스토리가 훌륭했어요.', DATE_SUB(NOW(), INTERVAL 17 DAY), 4, 0, 'ACTIVE', 2),
('<지저스 크라이스트 수퍼스타>의 메시지', '지저스 크라이스트 수퍼스타에서 종교적 갈등을 다룬 내용이 인상 깊었어요.', DATE_SUB(NOW(), INTERVAL 16 DAY), 5, 0, 'ACTIVE', 3),

('공연 소식: <레 미제라블> 재공연', '레 미제라블이 다시 공연된다는 소식이 너무 기대됩니다.', DATE_SUB(NOW(), INTERVAL 15 DAY), 1, 0, 'ACTIVE', 4),
('뮤지컬 <마틸다>의 어린 배우들', '마틸다에서 어린 배우들의 놀라운 연기력에 감탄했습니다.', DATE_SUB(NOW(), INTERVAL 14 DAY), 2, 0, 'ACTIVE', 5),
('<아이다>의 대서사시적인 스토리', '아이다의 장엄한 이야기와 강렬한 음악이 인상 깊었어요.', DATE_SUB(NOW(), INTERVAL 13 DAY), 3, 0, 'ACTIVE', 7),
('뮤지컬 <레베카>의 미스터리한 분위기', '레베카의 미스터리한 스토리와 매혹적인 무대가 인상적이었습니다.', DATE_SUB(NOW(), INTERVAL 12 DAY), 4, 0, 'ACTIVE', 1),
('<킹키부츠>의 감동적인 메시지', '킹키부츠의 자신을 있는 그대로 받아들이라는 메시지가 마음에 와닿았어요.', DATE_SUB(NOW(), INTERVAL 11 DAY), 5, 0, 'ACTIVE', 2),

('뮤지컬 <웨스트 사이드 스토리>의 사랑 이야기', '웨스트 사이드 스토리의 현대적인 로미오와 줄리엣 이야기가 매력적이었어요.', DATE_SUB(NOW(), INTERVAL 10 DAY), 1, 0, 'ACTIVE', 3),
('<레미제라블> 다시 보기', '레미제라블의 감동은 언제 다시 봐도 최고입니다.', DATE_SUB(NOW(), INTERVAL 9 DAY), 2, 0, 'ACTIVE', 4),
('뮤지컬 <드라큘라>의 매혹적인 캐릭터', '드라큘라의 미스터리하고 매혹적인 분위기가 인상 깊었습니다.', DATE_SUB(NOW(), INTERVAL 8 DAY), 3, 0, 'ACTIVE', 5),
('<피핀>의 혁신적인 무대 연출', '피핀의 독창적인 무대 연출과 스토리가 놀라웠어요.', DATE_SUB(NOW(), INTERVAL 7 DAY), 4, 0, 'ACTIVE', 7),
('<미스 사이공>의 슬픈 이야기', '미스 사이공에서 느낀 전쟁과 사랑의 비극적인 이야기가 감동적이었습니다.', DATE_SUB(NOW(), INTERVAL 6 DAY), 5, 0, 'ACTIVE', 1);
