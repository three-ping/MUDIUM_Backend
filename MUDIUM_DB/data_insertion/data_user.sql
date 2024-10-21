use mudiumdb;

INSERT INTO tbl_user (user_name, password, nickname, email, profile_image, created_at, signup_path, user_identifier,  user_role)
VALUES
    ('김슈슈', 'pw1234', 'shushu_ping', 'siusiu@example.com', NULL, '2023-08-01 10:30:00', 'NORMAL', CONCAT('NORMAL_', 'siusiu_ping'),'ROLE_MEMBER'),  -- user_identifier는 'NORMAL_alice123', user_auth_id는 사용자가 입력한 ID
    ('이효효', NULL, 'hyohyo', 'jinyjin@example.com', NULL, '2023-08-02 11:45:00', 'NAVER', CONCAT('NAVER_', '987654321'),'ROLE_MEMBER'),  -- user_identifier는 'NAVER_987654321', user_auth_id는 네이버의 고유번호 예시
    ('김서서', NULL, 'seoseo', 'hyunhyun@example.com', NULL, '2023-08-03 12:00:00', 'GOOGLE', CONCAT('GOOGLE_', 'abc123xyz'),'ROLE_ADMIN'),  -- user_identifier는 'GOOGLE_abc123xyz', user_auth_id는 구글의 고유번호 예시
    ('이우우', 'password123', 'woowoo', 'woowoo@example.com', NULL, '2023-08-04 09:15:00', 'NORMAL', CONCAT('NORMAL_', 'woowoo'),'ROLE_MEMBER'),  -- user_identifier는 'NORMAL_diana789', user_auth_id는 사용자가 입력한 ID
    ('김도옹', 'pwpw321','do_ong','doong@example.com',NULL, '2023-08-01 09:15:00','NORMAL',CONCAT('NORMAL_','do_ong'),'ROLE_MEMBER'),
    ('김모모', NULL, 'momo-ping', 'momo_ping@example.com', NULL, '2023-08-05 14:20:00', 'KAKAO', CONCAT('KAKAO_', 'kakao98765'),'ROLE_MEMBER');  -- user_identifier는 'KAKAO_kakao98765', user_auth_id는 카카오의 고유번호 예시
use mudiumdb;

