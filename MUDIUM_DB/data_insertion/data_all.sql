use mudiumdb;
INSERT INTO tbl_user (user_name, password, nickname, email, profile_image, created_at, signup_path, user_identifier,  user_role)
VALUES
    ('김슈슈', 'pw1234', 'shushu_ping', 'siusiu@example.com', NULL, '2023-08-01 10:30:00', 'NORMAL', CONCAT('NORMAL_', 'siusiu_ping'),'ROLE_MEMBER'),  -- user_identifier는 'NORMAL_alice123', user_auth_id는 사용자가 입력한 ID
    ('이효효', NULL, 'hyohyo', 'jinyjin@example.com', NULL, '2023-08-02 11:45:00', 'NAVER', CONCAT('NAVER_', '987654321'),'ROLE_MEMBER'),  -- user_identifier는 'NAVER_987654321', user_auth_id는 네이버의 고유번호 예시
    ('김서서', NULL, 'seoseo', 'hyunhyun@example.com', NULL, '2023-08-03 12:00:00', 'GOOGLE', CONCAT('GOOGLE_', 'abc123xyz'),'ROLE_ADMIN'),  -- user_identifier는 'GOOGLE_abc123xyz', user_auth_id는 구글의 고유번호 예시
    ('이우우', 'password123', 'woowoo', 'woowoo@example.com', NULL, '2023-08-04 09:15:00', 'NORMAL', CONCAT('NORMAL_', 'woowoo'),'ROLE_MEMBER'),  -- user_identifier는 'NORMAL_diana789', user_auth_id는 사용자가 입력한 ID
    ('김도옹', 'pwpw321','do_ong','doong@example.com',NULL, '2023-08-01 09:15:00','NORMAL',CONCAT('NORMAL_','do_ong'),'ROLE_MEMBER'),
    ('김모모', NULL, 'momo-ping', 'momo_ping@example.com', NULL, '2023-08-05 14:20:00', 'KAKAO', CONCAT('KAKAO_', 'kakao98765'),'ROLE_MEMBER');  -- user_identifier는 'KAKAO_kakao98765', user_auth_id는 카카오의 고유번호 예시

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


INSERT INTO `TBL_MUSICAL_INFO` (`title`, `image_url`, `rating`, `view_count`, `review_video`) VALUES
('잠자는 숲속의 공주', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239321_240416_114951.JPG', '전체 관람가', 1500, 'https://www.youtube.com/watch?v=dummy1'),
('오페라의 유령', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239322_240417_120000.JPG', '12세 이상', 5000, 'https://www.youtube.com/watch?v=dummy2'),
('라이온 킹', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239323_240418_130000.JPG', '전체 관람가', 3200, 'https://www.youtube.com/watch?v=dummy3'),
('위키드', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239324_240419_140000.JPG', '8세 이상', 4100, 'https://www.youtube.com/watch?v=dummy4'),
('맘마미아!', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239325_240420_150000.JPG', '전체 관람가', 2800, 'https://www.youtube.com/watch?v=dummy5'),
('캣츠', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239326_240421_160000.JPG', '전체 관람가', 3800, 'https://www.youtube.com/watch?v=dummy6'),
('레미제라블', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239327_240422_170000.JPG', '12세 이상', 4500, 'https://www.youtube.com/watch?v=dummy7'),
('시카고', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239328_240423_180000.JPG', '15세 이상', 3000, 'https://www.youtube.com/watch?v=dummy8'),
('아이다', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239329_240424_190000.JPG', '전체 관람가', 2200, 'https://www.youtube.com/watch?v=dummy9'),
('노트르담 드 파리', 'http://www.kopis.or.kr/upload/pfmPoster/PF_PF239330_240425_200000.JPG', '12세 이상', 3500, 'https://www.youtube.com/watch?v=dummy10');
INSERT INTO TBL_MUSICAL_BOARD (title, content, created_at, updated_at, view_count, `like`, active_status, user_id, musical_id)
VALUES
-- 뮤지컬 1: 뮤지컬 갈라쇼: 클라이막스
('갈라쇼 후기: 감동의 무대!', '클라이막스 공연을 보고 왔습니다. 정말 감동적이었어요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 1, 1),
('클라이막스 티켓 양도', '개인 사정으로 티켓을 양도합니다. 쪽지 주세요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 2, 1),
('클라이막스 OST 추천', '이 뮤지컬의 OST 중 최고는 뭐라고 생각하시나요?', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 3, 1),
('배우 A의 열연', 'A배우의 목소리가 정말 천상의 목소리였어요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 4, 1),
('클라이막스 관람 팁', '초보자를 위한 관람 팁을 공유합니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 5, 1),
('무대 세트 감상', '무대 세트가 정말 압권이었습니다. 여러분의 생각은?', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'INACTIVE', 6, 1),
('클라이막스 명장면 모음', '개인적으로 뽑은 명장면들입니다. 의견 주세요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 1, 1),
('공연장 주변 맛집', '공연 전후로 가볼만한 맛집 리스트입니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 2, 1),
('클라이막스 굿즈 후기', '새로 나온 굿즈 후기입니다. 퀄리티가 좋아요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 3, 1),
('다음 공연 기대평', '다음 공연은 어떨지 너무 기대됩니다. 여러분의 기대평은?', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 4, 1),

-- 뮤지컬 2: 애니: 공개 드레스 리허설
('애니 드레스 리허설 후기', '드레스 리허설을 보고 왔어요. 정말 기대돼요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 5, 2),
('애니 캐스팅 정보', '이번 애니 캐스팅 정보 공유합니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 6, 2),
('애니 OST 커버 영상', '제가 애니 OST를 커버해봤어요. 들어보세요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 1, 2),
('애니 의상 디자인', '이번 공연의 의상 디자인이 너무 예뻐요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'INACTIVE', 2, 2),
('애니 초보 관람객 팁', '처음 보시는 분들을 위한 팁 모음입니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 3, 2),
('애니 명대사 모음', '가장 마음에 들었던 대사들을 모아봤어요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 4, 2),
('애니 공연장 후기', '이번 공연장 시설이 정말 좋더라구요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 5, 2),
('애니 굿즈 양도', '중복된 굿즈가 있어서 양도합니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 6, 2),
('애니 리허설 비하인드', '리허설 때 있었던 재미있는 일화들!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 1, 2),
('애니 팬미팅 정보', '다가오는 애니 팬미팅 정보 공유합니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'INACTIVE', 2, 2),

-- 뮤지컬 3: 엄지공주
('엄지공주 초연 후기', '엄지공주 초연을 보고 왔습니다. 너무 좋았어요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 3, 3),
('엄지공주 캐스팅 소식', '새로운 캐스팅 정보가 나왔네요. 기대돼요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 4, 3),
('엄지공주 OST 감상', 'OST를 들으면서 공연을 되새겨봤어요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 5, 3),
('엄지공주 의상 디자인', '의상이 정말 아름답더라구요. 여러분 생각은?', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 6, 3),
('엄지공주 관람 꿀팁', '처음 보시는 분들을 위한 꿀팁 공유해요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 1, 3),
('엄지공주 명장면 모음', '가장 인상 깊었던 장면들을 모아봤어요.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'INACTIVE', 2, 3),
('엄지공주 공연장 시설', '이번 공연장 시설에 대한 후기입니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 3, 3),
('엄지공주 티켓 양도', '부득이한 사정으로 티켓 양도합니다.', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 4, 3),
('엄지공주 연습 현장', '연습 현장 스케치 사진 공유해요!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 5, 3),
('엄지공주 팬미팅 후기', '지난 팬미팅 다녀왔어요. 정말 좋았습니다!', NOW(), NOW(), FLOOR(RAND()*1000), 0, 'ACTIVE', 6, 3);
INSERT INTO `TBL_NOTICE` (`title`, `content`, `created_at`, `updated_at`, `view_count`, `user_id`)
VALUES
('공지사항 1', '공지사항 내용 1', NOW() - INTERVAL 30 DAY, NOW() - INTERVAL 30 DAY, 100, 1),
('공지사항 2', '공지사항 내용 2', NOW() - INTERVAL 29 DAY, NOW() - INTERVAL 29 DAY, 120, 1),
('공지사항 3', '공지사항 내용 3', NOW() - INTERVAL 28 DAY, NOW() - INTERVAL 28 DAY, 130, 1),
('공지사항 4', '공지사항 내용 4', NOW() - INTERVAL 27 DAY, NOW() - INTERVAL 27 DAY, 140, 1),
('공지사항 5', '공지사항 내용 5', NOW() - INTERVAL 26 DAY, NOW() - INTERVAL 26 DAY, 150, 1),
('공지사항 6', '공지사항 내용 6', NOW() - INTERVAL 25 DAY, NOW() - INTERVAL 25 DAY, 160, 1),
('공지사항 7', '공지사항 내용 7', NOW() - INTERVAL 24 DAY, NOW() - INTERVAL 24 DAY, 170, 1),
('공지사항 8', '공지사항 내용 8', NOW() - INTERVAL 23 DAY, NOW() - INTERVAL 23 DAY, 180, 1),
('공지사항 9', '공지사항 내용 9', NOW() - INTERVAL 22 DAY, NOW() - INTERVAL 22 DAY, 190, 1),
('공지사항 10', '공지사항 내용 10', NOW() - INTERVAL 21 DAY, NOW() - INTERVAL 21 DAY, 200, 1),
('공지사항 11', '공지사항 내용 11', NOW() - INTERVAL 20 DAY, NOW() - INTERVAL 20 DAY, 210, 1),
('공지사항 12', '공지사항 내용 12', NOW() - INTERVAL 19 DAY, NOW() - INTERVAL 19 DAY, 220, 1),
('공지사항 13', '공지사항 내용 13', NOW() - INTERVAL 18 DAY, NOW() - INTERVAL 18 DAY, 230, 1),
('공지사항 14', '공지사항 내용 14', NOW() - INTERVAL 17 DAY, NOW() - INTERVAL 17 DAY, 240, 1),
('공지사항 15', '공지사항 내용 15', NOW() - INTERVAL 16 DAY, NOW() - INTERVAL 16 DAY, 250, 1),
('공지사항 16', '공지사항 내용 16', NOW() - INTERVAL 15 DAY, NOW() - INTERVAL 15 DAY, 260, 1),
('공지사항 17', '공지사항 내용 17', NOW() - INTERVAL 14 DAY, NOW() - INTERVAL 14 DAY, 270, 1),
('공지사항 18', '공지사항 내용 18', NOW() - INTERVAL 13 DAY, NOW() - INTERVAL 13 DAY, 280, 1),
('공지사항 19', '공지사항 내용 19', NOW() - INTERVAL 12 DAY, NOW() - INTERVAL 12 DAY, 290, 1),
('공지사항 20', '공지사항 내용 20', NOW() - INTERVAL 11 DAY, NOW() - INTERVAL 11 DAY, 300, 1),
('공지사항 21', '공지사항 내용 21', NOW() - INTERVAL 10 DAY, NOW() - INTERVAL 10 DAY, 310, 1),
('공지사항 22', '공지사항 내용 22', NOW() - INTERVAL 9 DAY, NOW() - INTERVAL 9 DAY, 320, 1),
('공지사항 23', '공지사항 내용 23', NOW() - INTERVAL 8 DAY, NOW() - INTERVAL 8 DAY, 330, 1),
('공지사항 24', '공지사항 내용 24', NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 7 DAY, 340, 1),
('공지사항 25', '공지사항 내용 25', NOW() - INTERVAL 6 DAY, NOW() - INTERVAL 6 DAY, 350, 1);
INSERT INTO `TBL_REVIEW` (`content`, `created_at`, `updated_at`, `like`, `musical_id`, `user_id`, `active_status`) VALUES
('정말 감동적인 공연이었어요!', '2023-09-01 14:30:00', NULL, 120, 1, 1, 'ACTIVE'),
('음악과 무대가 너무 멋졌습니다!', '2023-09-02 16:45:00', NULL, 85, 2, 2, 'ACTIVE'),
('배우들의 연기가 인상 깊었어요.', '2023-09-03 18:00:00', NULL, 95, 3, 3, 'ACTIVE'),
('무대 연출이 뛰어나서 정말 놀랐습니다.', '2023-09-04 19:15:00', NULL, 75, 4, 4, 'ACTIVE'),
('스토리가 너무 감동적이었어요.', '2023-09-05 20:20:00', NULL, 65, 5, 5, 'ACTIVE'),
('다시 보고 싶은 공연입니다!', '2023-09-06 21:25:00', NULL, 100, 6, 6, 'ACTIVE'),
('음악이 너무 아름다웠습니다.', '2023-09-07 22:30:00', NULL, 110, 7, 1, 'ACTIVE'),
('라이브 공연의 매력을 잘 보여줬어요.', '2023-09-08 14:40:00', NULL, 90, 8, 2, 'ACTIVE'),
('배우들의 열정이 느껴지는 공연이었습니다.', '2023-09-09 15:50:00', NULL, 130, 9, 3, 'ACTIVE'),
('어린이들과 함께 보기 좋은 뮤지컬이에요.', '2023-09-10 16:55:00', NULL, 140, 10, 4, 'ACTIVE'),
('다소 지루한 부분도 있었지만 전체적으로는 좋았어요.', '2023-09-11 18:10:00', NULL, 50, 1, 5, 'ACTIVE'),
('완벽한 공연이었습니다!', '2023-09-12 19:25:00', NULL, 180, 2, 6, 'ACTIVE'),
('공연 시간도 적당하고, 연기도 훌륭했어요.', '2023-09-13 20:35:00', NULL, 155, 3, 1, 'ACTIVE'),
('스토리가 조금 더 흥미로웠으면 좋았을 것 같아요.', '2023-09-14 21:45:00', NULL, 70, 4, 2, 'ACTIVE'),
('다른 뮤지컬도 보고 싶어지게 만든 공연이에요.', '2023-09-15 22:55:00', NULL, 200, 5, 3, 'ACTIVE'),
('음향이 조금 아쉬웠지만 전반적으로 좋았어요.', '2023-09-16 14:00:00', NULL, 60, 6, 4, 'ACTIVE'),
('친구와 함께 봤는데 정말 재밌었어요!', '2023-09-17 15:10:00', NULL, 95, 7, 5, 'ACTIVE'),
('생생한 무대 연출이 인상적이었어요.', '2023-09-18 16:20:00', NULL, 105, 8, 6, 'ACTIVE'),
('어린이들에게 좋은 교육적인 내용이 많아요.', '2023-09-19 17:30:00', NULL, 85, 9, 1, 'ACTIVE'),
('다소 아쉬운 부분도 있었지만 전체적으로 만족합니다.', '2023-09-20 18:40:00', NULL, 65, 10, 2, 'ACTIVE');

INSERT INTO `TBL_SECRET_REVIEW` (`content`, `created_at`, `updated_at`, `active_status`, `musical_id`, `user_id`) VALUES
('훌륭한 공연이었어요. 음악과 무대가 정말 멋졌어요!', '2023-08-01 12:00:00', NULL, 'ACTIVE', 1, 1),
('생각보다 지루했어요. 그래도 배우들의 연기는 좋았어요.', '2023-08-02 13:00:00', NULL, 'ACTIVE', 2, 2),
('오페라의 유령은 언제 봐도 감동적이네요.', '2023-08-03 14:00:00', NULL, 'ACTIVE', 2, 3),
('아이들이 좋아할 만한 공연이에요. 가족과 함께 보세요.', '2023-08-04 15:00:00', NULL, 'ACTIVE', 3, 4),
('위키드의 마녀 캐릭터가 정말 인상적이었어요.', '2023-08-05 16:00:00', NULL, 'ACTIVE', 4, 5),
('맘마미아!는 언제나 신나는 노래들로 가득하네요.', '2023-08-06 17:00:00', NULL, 'ACTIVE', 5, 6),
('캣츠는 춤과 노래 모두 훌륭했어요.', '2023-08-07 18:00:00', NULL, 'ACTIVE', 6, 1),
('레미제라블의 감동적인 이야기, 정말 최고였어요.', '2023-08-08 19:00:00', NULL, 'ACTIVE', 7, 2),
('시카고는 성인 관객에게 추천합니다. 연출이 강렬해요.', '2023-08-09 20:00:00', NULL, 'ACTIVE', 8, 3),
('아이다의 스토리가 정말 아름답고 감동적이에요.', '2023-08-10 21:00:00', NULL, 'ACTIVE', 9, 4),
('노트르담 드 파리의 무대 디자인이 정말 인상적이에요.', '2023-08-11 22:00:00', NULL, 'ACTIVE', 10, 5),
('공연장 분위기가 너무 좋았어요. 또 가고 싶어요!', '2023-08-12 23:00:00', NULL, 'ACTIVE', 1, 6),
('뮤지컬의 모든 요소가 완벽히 조화롭네요.', '2023-08-13 10:00:00', NULL, 'ACTIVE', 2, 1),
('음악이 너무 감동적이었어요. 눈물이 났어요.', '2023-08-14 11:00:00', NULL, 'ACTIVE', 3, 2),
('배우들의 에너지가 대단했어요!', '2023-08-15 12:00:00', NULL, 'ACTIVE', 4, 3),
('정말 재미있었어요. 다음에 또 보고 싶어요.', '2023-08-16 13:00:00', NULL, 'ACTIVE', 5, 4),
('화려한 무대와 노래에 매료되었어요.', '2023-08-17 14:00:00', NULL, 'ACTIVE', 6, 5),
('레미제라블은 정말 감동 그 자체였어요.', '2023-08-18 15:00:00', NULL, 'ACTIVE', 7, 6),
('배우들의 연기력이 압권이었어요!', '2023-08-19 16:00:00', NULL, 'ACTIVE', 8, 1),
('무대 효과가 정말 대단했어요. 추천합니다!', '2023-08-20 17:00:00', NULL, 'ACTIVE', 9, 2),
('다시 보고 싶은 공연이에요.', '2023-08-21 18:00:00', NULL, 'ACTIVE', 10, 3);
UPDATE TBL_MUSICAL_INFO
SET title = '토끼와 거북이',
    synopsys = '토끼와 거북이는 전래동화의 이야기를 바탕으로, 경쟁과 우정, 인내에 대한 교훈을 그린 가족 뮤지컬입니다. 빠른 토끼와 느린 거북이가 경주를 하며 서로의 차이를 인정하고 우정을 쌓아가는 과정을 감동적이고 유머러스하게 그려냅니다.'
WHERE title = '토끼와 거북이';

UPDATE TBL_MUSICAL_INFO
SET title = '빨래',
    synopsys = '빨래는 서울에서 힘겹게 살아가는 청춘들의 일상과 꿈, 그리고 사랑을 그린 뮤지컬입니다. 다양한 인물들의 삶을 통해 현대인의 고민과 희망을 따뜻하게 풀어낸 작품으로, 잔잔한 감동과 웃음을 선사합니다.'
WHERE title = '빨래';

UPDATE TBL_MUSICAL_INFO
SET title = '겨울왕국: 겨울이야기',
    synopsys = '겨울왕국: 겨울이야기는 디즈니 애니메이션 "겨울왕국"을 뮤지컬로 각색한 작품으로, 엘사와 안나 자매의 모험을 중심으로 한 감동적인 이야기입니다. 겨울의 마법과 사랑의 힘이 돋보이는 노래와 무대가 가족 관객에게 큰 인기를 끌고 있습니다.'
WHERE title = '겨울왕국: 겨울이야기';

UPDATE TBL_MUSICAL_INFO
SET title = '옹알스',
    synopsys = '옹알스는 대사 없이 몸짓과 표정만으로 웃음을 선사하는 넌버벌 퍼포먼스입니다. 다양한 코미디적 상황을 통해 전 세계 관객들에게 웃음을 주며, 유쾌하고 따뜻한 메시지를 전달하는 공연입니다.'
WHERE title = '옹알스';

UPDATE TBL_MUSICAL_INFO
SET title = '라푼젤',
    synopsys = '라푼젤은 긴 머리를 가진 소녀 라푼젤이 탑에서 탈출하여 자신의 진정한 운명을 찾는 이야기를 그린 뮤지컬입니다. 동화 속 이야기에 아름다운 음악과 무대 연출이 더해져 가족 관객들에게 큰 사랑을 받는 작품입니다.'
WHERE title = '라푼젤';

UPDATE TBL_MUSICAL_INFO
SET title = '인어공주',
    synopsys = '인어공주는 바다 속 왕국에 사는 인어공주가 인간 세상에 대한 동경을 품고 육지로 올라와 사랑을 찾는 이야기를 그린 뮤지컬입니다. 아름다운 음악과 화려한 무대 연출로 가족 관객들에게 큰 인기를 끌고 있습니다.'
WHERE title = '인어공주';

UPDATE TBL_MUSICAL_INFO
SET title = '구름빵',
    synopsys = '구름빵은 하늘을 나는 신비한 빵을 먹고 모험을 떠나는 남매의 이야기를 그린 가족 뮤지컬입니다. 구름빵의 힘을 빌려 하늘을 나는 특별한 경험을 통해 서로의 사랑을 확인하는 이야기가 따뜻하게 그려집니다.'
WHERE title = '구름빵';

UPDATE TBL_MUSICAL_INFO
SET title = '콩쥐팥쥐',
    synopsys = '전래동화 "콩쥐팥쥐"를 각색한 뮤지컬로, 콩쥐의 고난과 결국 그녀의 선함이 보상받는 이야기를 아름다운 음악과 함께 담아냈습니다. 가족 관객들이 즐길 수 있는 감동적인 작품입니다.'
WHERE title = '콩쥐팥쥐';

UPDATE TBL_MUSICAL_INFO
SET title = '오즈의 마법사',
    synopsys = '뮤지컬 "오즈의 마법사"는 도로시와 그녀의 친구들이 오즈의 마법사를 찾아 떠나는 모험을 그린 작품입니다. 판타지적인 요소와 감동적인 이야기가 어우러져 어린이와 가족 관객들에게 큰 인기를 끌고 있습니다.'
WHERE title = '오즈의 마법사';

UPDATE TBL_MUSICAL_INFO
SET title = '브람스',
    synopsys = '브람스는 작곡가 요하네스 브람스의 삶과 사랑, 그리고 음악에 대한 열정을 그린 뮤지컬입니다. 그의 대표적인 음악과 함께 인생의 중요한 순간들을 감동적으로 그려낸 작품입니다.'
WHERE title = '브람스';

UPDATE TBL_MUSICAL_INFO
SET title = '앤 ANNE',
    synopsys = '앤은 루시 모드 몽고메리의 소설 "빨강머리 앤"을 바탕으로 한 뮤지컬입니다. 앤 셜리의 성장과정과 그녀가 그린 꿈을 아름다운 음악과 함께 감동적으로 풀어냅니다.'
WHERE title = '앤 ANNE';

UPDATE TBL_MUSICAL_INFO
SET title = '스즈메의 문단속, 날씨의 아이, 너의 이름은. 갈라 콘서트 (9월)',
    synopsys = '스즈메의 문단속, "날씨의 아이", "너의 이름은"의 대표 곡들을 한자리에서 들을 수 있는 갈라 콘서트입니다. 감동적인 장면과 음악이 어우러져 애니메이션 팬과 음악 애호가들에게 특별한 경험을 선사합니다.'
WHERE title = '스즈메의 문단속, 날씨의 아이, 너의 이름은. 갈라 콘서트 (9월)';

UPDATE TBL_MUSICAL_INFO
SET title = '엄유민법 콘서트',
    synopsys = '엄유민법 콘서트는 유명 뮤지컬 배우 엄기준, 유준상, 민영기, 김법래가 모여 선보이는 뮤지컬 콘서트입니다. 각 배우들이 출연한 뮤지컬의 대표 곡들을 공연하며 팬들에게 특별한 시간을 선사합니다.'
WHERE title = '엄유민법 콘서트';

UPDATE TBL_MUSICAL_INFO
SET title = '제2회 대한민국 국제뮤지컬콩쿠르 결선',
    synopsys = '제2회 대한민국 국제뮤지컬콩쿠르는 전 세계 뮤지컬 배우들이 실력을 겨루는 대회로, 결선에서는 뛰어난 참가자들이 최고의 무대를 선보입니다. 결선 무대는 그들의 열정과 실력을 한눈에 볼 수 있는 기회입니다.'
WHERE title = '제2회 대한민국 국제뮤지컬콩쿠르 결선';

UPDATE TBL_MUSICAL_INFO
SET title = '1919',
    synopsys = '뮤지컬 "1919"는 대한민국 임시정부 수립과 독립운동을 배경으로 한 작품입니다. 역사적 사건 속에서 인물들이 겪는 갈등과 희생, 그리고 독립을 향한 열망을 드라마틱하게 그려냅니다.'
WHERE title = '1919';

UPDATE TBL_MUSICAL_INFO
SET title = '셜록홈즈 시즌2, 네 사람의 서명',
    synopsys = '셜록홈즈 시즌2: 네 사람의 서명은 명탐정 셜록 홈즈가 복잡한 미스터리를 해결하는 과정을 그린 뮤지컬입니다. 이번 시즌에서는 새로운 인물들과 함께 더 긴장감 넘치는 사건들이 펼쳐지며, 관객들에게 추리의 재미를 선사합니다.'
WHERE title = '셜록홈즈 시즌2, 네 사람의 서명';

UPDATE TBL_MUSICAL_INFO
SET title = '스크린콘서트, 뮤지컬의 밤: 선샤인 온 리스',
    synopsys = '선샤인 온 리스의 명장면을 스크린으로 감상할 수 있는 특별한 뮤지컬 콘서트입니다. 뮤지컬 팬들에게는 새로운 방식으로 뮤지컬을 즐길 수 있는 기회를 제공합니다.'
WHERE title = '스크린콘서트, 뮤지컬의 밤: 선샤인 온 리스';

INSERT INTO TBL_RECOMMENDED_MUSICAL (title, content, created_at, user_id) VALUES
                                                                              ('Les Misérables', '빅토르 위고의 소설을 바탕으로 한 작품으로, 프랑스 혁명 시대를 배경으로 합니다. 감동적인 스토리와 웅장한 음악으로 많은 사람들에게 사랑받는 뮤지컬입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('The Phantom of the Opera', '앤드루 로이드 웨버의 걸작으로, 파리 오페라하우스를 배경으로 한 사랑과 집착의 이야기를 다룬 뮤지컬입니다. 화려한 무대와 웅장한 음악이 매력적입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Wicked', '오즈의 마법사의 뒤에 숨겨진 이야기를 재해석한 작품입니다. 등장인물의 깊이와 무대 연출이 인상적인 이 뮤지컬은 마법과 우정, 차별에 대한 이야기를 담고 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Mamma Mia!', 'ABBA의 명곡들을 바탕으로 한 유쾌하고 밝은 뮤지컬입니다. 그리스의 한 섬에서 벌어지는 결혼식과 관련된 이야기를 그리며, 가벼운 마음으로 즐길 수 있는 작품입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('The Lion King', '디즈니의 애니메이션을 원작으로 한 작품으로, 화려한 의상과 안무가 돋보입니다. 애니메이션의 감동적인 스토리와 더불어 웅장한 무대가 볼거리입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Aladdin', '디즈니의 또 다른 히트작을 뮤지컬화한 작품입니다. 신비로운 무대 연출과 친숙한 음악들로 초보자들에게 쉽게 다가갈 수 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Rent', '90년대 뉴욕을 배경으로, 젊은 예술가들의 사랑과 삶, 고난을 다룬 작품입니다. 현대적인 감성과 록 음악이 인상적이며, 열정적인 분위기가 특징입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Hamilton', '미국 건국의 아버지 중 한 명인 알렉산더 해밀턴의 삶을 그린 뮤지컬로, 힙합, R&B 등 다양한 장르의 음악과 역동적인 무대가 돋보입니다. 최근 큰 인기를 끌고 있는 작품입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Chicago', '1920년대 시카고를 배경으로 한 범죄와 쇼비즈니스의 세계를 다룬 뮤지컬입니다. 재즈 음악과 화려한 댄스, 블랙 코미디 요소가 잘 어우러져 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Billy Elliot', '영국 탄광촌의 소년 빌리가 발레를 배우며 겪는 성장기를 다룬 뮤지컬입니다. 감동적인 스토리와 열정적인 춤이 잘 어우러져 초보자들에게 추천하기 좋은 작품입니다.', CURRENT_TIMESTAMP, NULL);

INSERT INTO TBL_MUSICAL_TERMS (musical_terms, musical_terms_definition, created_at, user_id) VALUES
                                                                                                 ('넘버', '뮤지컬에 등장하는 모든 노래를 의미하며, 대본과 악보에서 각 노래에 번호가 붙어있는 것에서 유래되었습니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('앙상블', '주연 배우 외 여러 역할을 맡으며 무대를 채우는 배우들을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('티켓팅', '티켓을 구매하는 과정을 말합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('피켓팅', '매우 치열한 티켓팅을 뜻하는 은어입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('캐스팅', '뮤지컬 출연 배우를 선정하는 과정입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('인터미션', '1막과 2막 사이 휴식 시간을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오버추어', '뮤지컬 시작을 알리는 서곡입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('커튼콜', '공연 후 배우들이 등장해 관객의 박수를 받는 장면입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('마티네', '낮 시간에 열리는 공연을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오케스트라 피트', '오케스트라가 위치한 무대 아래 공간을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('프리뷰', '정식 공연 전 시범 공연을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('프레스콜', '언론을 대상으로 하는 공연 시연입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('OP석', '오케스트라 피트 바로 앞 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('중블', '무대 중앙 구역에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오블', '무대 오른쪽에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('왼블', '무대 왼쪽에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('극사', '좌석 끝에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('시야 제한석', '무대 일부가 보이지 않는 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('하나님석', '극장의 가장 높은 층에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL);

INSERT INTO TBL_ETIQUETTE (etiquette, etiquette_description, created_at)
VALUES
    ('공연 시작 전 도착하기', '공연 시작 10-15분 전에 도착하여 자리에 앉아 공연을 준비하는 것이 좋습니다.', CURRENT_TIMESTAMP),
    ('휴대폰 사용 금지', '공연 중에는 휴대폰을 꺼두거나 비행기 모드로 설정하여 방해가 되지 않도록 해야 합니다.', CURRENT_TIMESTAMP),
    ('음식과 음료', '공연 중에는 음식과 음료를 섭취하지 않는 것이 좋습니다. 관객과 배우 모두에 대한 예의입니다.', CURRENT_TIMESTAMP),
    ('조용히 관람하기', '공연 중에는 대화나 소리를 내지 말고 조용히 관람해야 합니다. 이는 다른 관객에 대한 예의입니다.', CURRENT_TIMESTAMP),
    ('박수와 환호', '좋은 장면 후에는 박수와 환호로 배우들에게 응원해 주세요. 이는 그들의 노력에 대한 감사의 표현입니다.', CURRENT_TIMESTAMP),
    ('중간 퇴장 자제', '가능하면 1막과 2막 사이의 인터미션 동안만 자리를 비우는 것이 좋습니다. 공연 중간에 퇴장하는 것은 예의에 어긋납니다.', CURRENT_TIMESTAMP),
    ('복장 규정 준수', '공연에 맞는 복장을 갖추는 것이 중요하며, 주최측의 복장 규정을 따르는 것이 좋습니다.', CURRENT_TIMESTAMP),
    ('자녀 동반 시 주의', '어린이를 동반할 경우, 공연에 적합한 나이인지 확인하고 관람 매너를 잘 지킬 수 있도록 지도해야 합니다.', CURRENT_TIMESTAMP);







