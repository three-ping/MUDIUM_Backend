
DROP DATABASE IF EXISTS mudiumdb;
CREATE DATABASE MUDIUMDB;
USE MUDIUMDB;

CREATE TABLE TBL_USER (
                          user_id BIGINT(20) NOT NULL AUTO_INCREMENT,
                          user_name VARCHAR(255) NOT NULL,
                          password VARCHAR(255),
                          nickname VARCHAR(255),
                          email VARCHAR(255),
                          user_status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE' CHECK(user_status IN ('ACTIVE','INACTIVE')),
                          created_at TIMESTAMP,
                          withdrawn_at TIMESTAMP,
                          profile_image TEXT,
                          accept_status VARCHAR(255) NOT NULL DEFAULT 'N' CHECK(accept_status IN ('Y','N')),
                          signup_path VARCHAR(255) CHECK (signup_path IN ('KAKAO','NAVER','GOOGLE','NORMAL')),  -- 가입 경로는 KAKAO,NAVER,GOOGLE,NORMAL
                          user_identifier VARCHAR(511) NOT NULL,  -- 신규 추가, 가입 경로 + email(or 고유번호)
                          user_role VARCHAR(256) NOT NULL,
                          PRIMARY KEY (user_id),
                          UNIQUE KEY unique_user_identifier (user_identifier)  -- 유니크 인덱스 추가
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='회원' DEFAULT CHARSET=UTF8;
