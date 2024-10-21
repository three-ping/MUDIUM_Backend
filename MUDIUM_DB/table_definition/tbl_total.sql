DROP TABLE IF EXISTS `TBL_USER_CUSTOM_TICKET`;
DROP TABLE IF EXISTS `TBL_SECRET_REVIEW`;
DROP TABLE IF EXISTS `TBL_REVIEW_LIKE`;
DROP TABLE IF EXISTS `TBL_REVIEW_COMMENT`;
DROP TABLE IF EXISTS `TBL_MUSICAL_BOARD_REPLY`;
DROP TABLE IF EXISTS `TBL_MUSICAL_BOARD_LIKE`;
DROP TABLE IF EXISTS `TBL_MUSICAL_BOARD_COMMENT`;
DROP TABLE IF EXISTS `TBL_MUSICAL_BOARD`;
DROP TABLE IF EXISTS `TBL_INQUIRY_COMMENT`;
DROP TABLE IF EXISTS `TBL_CALENDAR_THEME`;
DROP TABLE IF EXISTS `TBL_BOOKMARK`;
DROP TABLE IF EXISTS `TBL_BOARD_REPLY`;
DROP TABLE IF EXISTS `TBL_BOARD_LIKE`;
DROP TABLE IF EXISTS `TBL_BOARD_COMMENT`;
DROP TABLE IF EXISTS `TBL_SCOPE`;
DROP TABLE IF EXISTS `TBL_REVIEW`;
DROP TABLE IF EXISTS `TBL_PERFORMANCE_RANK`;
DROP TABLE IF EXISTS `TBL_PERFORMANCE`;
DROP TABLE IF EXISTS `TBL_RECOMMENDED_MUSICAL`;
DROP TABLE IF EXISTS `TBL_ETIQUETTE`;
DROP TABLE IF EXISTS `TBL_MUSICAL_TERMS`;
DROP TABLE IF EXISTS `TBL_NOTICE`;
DROP TABLE IF EXISTS `TBL_INQUIRY`;
DROP TABLE IF EXISTS `TBL_BOARD`;
DROP TABLE IF EXISTS `TBL_CUSTOM_TICKET`;
DROP TABLE IF EXISTS `TBL_MUSICAL_INFO`;
DROP TABLE IF EXISTS `TBL_USER`;

CREATE TABLE TBL_USER
(
    user_id         BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_name       VARCHAR(255) NOT NULL,
    password        VARCHAR(255),
    nickname        VARCHAR(255),
    email           VARCHAR(255),
    user_status     VARCHAR(255) NOT NULL DEFAULT 'ACTIVE' CHECK (user_status IN ('ACTIVE', 'INACTIVE')),
    created_at      TIMESTAMP,
    withdrawn_at    TIMESTAMP,
    profile_image   TEXT,
    accept_status   VARCHAR(255) NOT NULL DEFAULT 'N' CHECK (accept_status IN ('Y', 'N')),
    signup_path     VARCHAR(255) CHECK (signup_path IN ('KAKAO', 'NAVER', 'GOOGLE', 'NORMAL')), -- 가입 경로는 KAKAO,NAVER,GOOGLE,NORMAL
    user_identifier VARCHAR(511) NOT NULL,                                                      -- 신규 추가, 가입 경로 + 이메일(or 고유번호)
    user_role       VARCHAR(256) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY unique_user_identifier (user_identifier)                                         -- 유니크 인덱스 추가
) ENGINE=INNODB AUTO_INCREMENT=1 COMMENT='회원' DEFAULT CHARSET=UTF8;


CREATE TABLE `TBL_MUSICAL_INFO`
(
    `musical_info_id` BIGINT        NOT NULL AUTO_INCREMENT COMMENT '뮤지컬ID',
    `title`           VARCHAR(1023) NOT NULL COMMENT '뮤지컬제목',
    `image_url`       VARCHAR(1023) COMMENT '포스터',
    `rating`          VARCHAR(255)  COMMENT '관람등급',
    `view_count`      BIGINT  DEFAULT 0  COMMENT '조회 수',
    `review_video`    TEXT COMMENT '리뷰 영상',
    `production`      VARCHAR(255)  COMMENT '제작사',
    `synopsys`        TEXT COMMENT '시놉시스',
    PRIMARY KEY (`musical_info_id`)
) COMMENT = '뮤지컬 정보';

CREATE TABLE tbl_custom_ticket (
    custom_ticket_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_image LONGTEXT, 
    hologram_color1 VARCHAR(255), 
    hologram_color2 VARCHAR(255),  
    comment VARCHAR(500),  
    user_id BIGINT NOT NULL,  

    CONSTRAINT FK_user_customticket FOREIGN KEY (user_id) REFERENCES tbl_user(user_id)
);


CREATE TABLE `TBL_BOARD`
(
    `board_id`      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '자유게시글ID',
    `title`         VARCHAR(1023) NOT NULL COMMENT '제목',
    `content`       TEXT          NOT NULL COMMENT '내용',
    `created_at`    TIMESTAMP     NOT NULL COMMENT '작성일자',
    `updated_at`    TIMESTAMP COMMENT '수정일자',
    `view_count`    BIGINT      DEFAULT 0  NOT NULL COMMENT '조회수',
    `board_like`          BIGINT        NOT NULL COMMENT '좋아요',
    `comments`       BIGINT NOT NULL DEFAULT 0 COMMENT '댓글수',
    `active_status` VARCHAR(255)  NOT NULL COMMENT '활성화상태',
    `user_id`       BIGINT COMMENT '회원ID',
    PRIMARY KEY (`board_id`),
    CONSTRAINT fk_board_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '자유게시글';

CREATE TABLE `TBL_INQUIRY`
(
    `inquiry_id` BIGINT        NOT NULL AUTO_INCREMENT COMMENT '문의게시글ID',
    `title`      VARCHAR(1023) NOT NULL COMMENT '문의게시글제목',
    `content`    TEXT          NOT NULL COMMENT '문의게시글내용',
    `created_at` TIMESTAMP     NOT NULL COMMENT '문의게시글작성일자',
    `updated_at` TIMESTAMP COMMENT '문의게시글수정일자',
    `comments`       BIGINT NOT NULL DEFAULT 0 COMMENT '댓글수',
    `user_id`    BIGINT COMMENT '회원ID',
    PRIMARY KEY (`inquiry_id`),
    CONSTRAINT fk_inquiry_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '문의게시글';

CREATE TABLE `TBL_NOTICE`
(
    `notice_id`  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '공지게시글ID',
    `title`      VARCHAR(255) NOT NULL COMMENT '공지게시글제목',
    `content`    TEXT         NOT NULL COMMENT '공지게시글내용',
    `created_at` TIMESTAMP    NOT NULL COMMENT '공지게시글작성일자',
    `updated_at` TIMESTAMP COMMENT '공지게시글수정일자',
    `view_count` BIGINT      default 0 NOT NULL COMMENT '공지게시글조회수',
    `user_id`    BIGINT COMMENT '회원ID',
    PRIMARY KEY (`notice_id`),
    CONSTRAINT fk_notice_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '공지게시글';

CREATE TABLE `TBL_ETIQUETTE`
(
    `etiquette_id`          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '관람 매너ID',
    `etiquette`             VARCHAR(1023) NOT NULL COMMENT '관람 매너',
    `etiquette_description` TEXT          NOT NULL COMMENT '관람 매너 설명',
    `created_at`            TIMESTAMP     NOT NULL COMMENT '관람 매너 작성일자',
    `updated_at`            TIMESTAMP COMMENT '관람 매너 수정일자',
    `user_id`               BIGINT COMMENT '회원ID',
    PRIMARY KEY (`etiquette_id`),
    CONSTRAINT fk_etiquette_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '관람 매너';

CREATE TABLE `TBL_RECOMMENDED_MUSICAL`
(
    `recommended_musical_id` BIGINT        NOT NULL AUTO_INCREMENT COMMENT '추천뮤지컬ID',
    `title`                  VARCHAR(1023) NOT NULL COMMENT '뮤지컬제목',
    `content`                TEXT          NOT NULL COMMENT '뮤지컬내용',
    `created_at`             TIMESTAMP     NOT NULL COMMENT '뮤지컬추천작성일자',
    `updated_at`             TIMESTAMP COMMENT '뮤지컬추천수정일자',
    `user_id`                BIGINT COMMENT '회원ID',
    PRIMARY KEY (`recommended_musical_id`),
    CONSTRAINT fk_recommended_musical_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '추천 뮤지컬';

CREATE TABLE `TBL_PERFORMANCE`
(
    `performance_id`  BIGINT        NOT NULL AUTO_INCREMENT COMMENT '공연ID',
    `region`          VARCHAR(255)  NOT NULL COMMENT '공연 지역',
    `start_date`      TIMESTAMP     NOT NULL COMMENT '공연시작일',
    `end_date`        TIMESTAMP COMMENT '공연종료일',
    `runtime`         VARCHAR(255)  NOT NULL COMMENT '공연 시간',
    `theater`         VARCHAR(255)  NOT NULL COMMENT '극장 이름',
    `actor_list`      VARCHAR(1023) NOT NULL COMMENT '배우 명단',
    `poster`          VARCHAR(1023) COMMENT '공연 포스터',
    `musical_info_id` BIGINT COMMENT '뮤지컬 ID',
    PRIMARY KEY (`performance_id`),
    CONSTRAINT fk_performance_musical FOREIGN KEY (`musical_info_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`)
) COMMENT = '공연정보';

CREATE TABLE TBL_MUSICAL_TERMS
(
    `musical_terms_id`         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '뮤지컬 용어ID',
    `musical_terms`            VARCHAR(1023) NOT NULL COMMENT '뮤지컬 용어',
    `musical_terms_definition` TEXT          NOT NULL COMMENT '뮤지컬 용어 정의',
    `created_at`               TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일자',
    `updated_at`               TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
    `user_id`                  BIGINT COMMENT '회원ID',
    PRIMARY KEY (`musical_terms_id`),
    CONSTRAINT fk_musical_terms_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '뮤지컬 용어';

CREATE TABLE `TBL_REVIEW`
(
    `review_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '리뷰ID',
    `content`       TEXT         NOT NULL COMMENT '내용',
    `created_at`    TIMESTAMP    NOT NULL COMMENT '작성 시각',
    `updated_at`    TIMESTAMP COMMENT '수정 시각',
    `like`          BIGINT       NOT NULL COMMENT '리뷰 좋아요',
    `comments`       BIGINT NOT NULL DEFAULT 0 COMMENT '댓글수',
    `musical_id`    BIGINT COMMENT '뮤지컬ID',
    `user_id`       BIGINT COMMENT '회원ID',
    `active_status` VARCHAR(255) NOT NULL COMMENT '활성화 여부',
    PRIMARY KEY (`review_id`),
    CONSTRAINT fk_review_musical FOREIGN KEY (`musical_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`),
    CONSTRAINT fk_review_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '리뷰';

CREATE TABLE `TBL_SCOPE`
(
    `user_id`         BIGINT    NOT NULL COMMENT '회원ID',
    `musical_info_id` BIGINT    NOT NULL COMMENT '뮤지컬ID',
    `scope`           DOUBLE   NOT NULL COMMENT '별점',
    `created_at`      TIMESTAMP NOT NULL COMMENT '별점 작성시각',
    `updated_at`      TIMESTAMP COMMENT '별점 수정시각',
    `user_nickname`    VARCHAR(255) NOT NULL COMMENT '유저닉네임',
    PRIMARY KEY (`user_id`, `musical_info_id`),
    CONSTRAINT fk_scope_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`),
    CONSTRAINT fk_scope_musical FOREIGN KEY (`musical_info_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`)
) COMMENT = '별점';

CREATE TABLE `TBL_BOARD_COMMENT`
(
    `board_comment_id` BIGINT       NOT NULL AUTO_INCREMENT COMMENT '댓글ID',
    `content`          TEXT         NOT NULL COMMENT '내용',
    `created_at`       TIMESTAMP    NOT NULL COMMENT '작성시각',
    `active_status`    VARCHAR(255) NOT NULL COMMENT '활성화상태',
    `updated_at`       TIMESTAMP COMMENT '수정시각',
    `board_id`         BIGINT COMMENT '자유게시글ID',
    `user_id`          BIGINT COMMENT '회원ID',
    PRIMARY KEY (`board_comment_id`),
    CONSTRAINT fk_board_comment_board FOREIGN KEY (`board_id`) REFERENCES `TBL_BOARD` (`board_id`),
    CONSTRAINT fk_board_comment_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '자유게시글댓글';

CREATE TABLE `TBL_BOARD_LIKE`
(
    `board_id` BIGINT NOT NULL COMMENT '자유게시글ID',
    `user_id`  BIGINT NOT NULL COMMENT '회원ID',
    PRIMARY KEY (`board_id`, `user_id`),
    CONSTRAINT fk_board_like_board FOREIGN KEY (`board_id`) REFERENCES `TBL_BOARD` (`board_id`),
    CONSTRAINT fk_board_like_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '자유게시글좋아요';

CREATE TABLE `TBL_BOARD_REPLY`
(
    `board_reply_id`   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '대댓글ID',
    `content`          TEXT         NOT NULL COMMENT '내용',
    `created_at`       TIMESTAMP    NOT NULL COMMENT '작성시각',
    `updated_at`   TIMESTAMP  COMMENT '수정시각',
    `active_status`    VARCHAR(255) NOT NULL COMMENT '활성화상태',
    `board_comment_id` BIGINT COMMENT '댓글ID',
    `user_id`          BIGINT COMMENT '회원ID',
    PRIMARY KEY (`board_reply_id`),
    CONSTRAINT fk_board_reply_comment FOREIGN KEY (`board_comment_id`) REFERENCES `TBL_BOARD_COMMENT` (`board_comment_id`),
    CONSTRAINT fk_board_reply_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '자유게시글대댓글';

CREATE TABLE `TBL_BOOKMARK`
(
    `musical_info_id` BIGINT NOT NULL COMMENT '뮤지컬ID',
    `user_id`         BIGINT NOT NULL COMMENT '회원ID',
    PRIMARY KEY (`musical_info_id`, `user_id`),
    CONSTRAINT fk_bookmark_musical FOREIGN KEY (`musical_info_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`),
    CONSTRAINT fk_bookmark_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '북마크';

CREATE TABLE `TBL_CALENDAR_THEME`
(
    `calendar_theme_id` BIGINT        NOT NULL AUTO_INCREMENT COMMENT '캘린더ID',
    `path`              VARCHAR(1023) NOT NULL COMMENT '캘린더테마경로',
    `user_id`           BIGINT COMMENT '회원ID',
    `musical_info_id`   BIGINT COMMENT '뮤지컬ID',
    PRIMARY KEY (`calendar_theme_id`),
    CONSTRAINT fk_calendar_theme_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`),
    CONSTRAINT fk_calendar_theme_musical FOREIGN KEY (`musical_info_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`)
) COMMENT = '캘린더 테마';

CREATE TABLE `TBL_INQUIRY_COMMENT`
(
    `inquiry_comment_id` BIGINT    NOT NULL AUTO_INCREMENT COMMENT '문의댓글ID',
    `content`            TEXT      NOT NULL COMMENT '내용',
    `created_at`         TIMESTAMP NOT NULL COMMENT '작성시각',
    `updated_at`         TIMESTAMP COMMENT '수정시각',
    `user_id`            BIGINT COMMENT '회원ID',
    `inquiry_id`         BIGINT COMMENT '문의게시글ID',
    PRIMARY KEY (`inquiry_comment_id`),
    CONSTRAINT fk_inquiry_comment_inquiry FOREIGN KEY (`inquiry_id`) REFERENCES `TBL_INQUIRY` (`inquiry_id`),
    CONSTRAINT fk_inquiry_comment_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '문의게시글댓글';

CREATE TABLE `TBL_MUSICAL_BOARD`
(
    `musical_board_id` BIGINT        NOT NULL AUTO_INCREMENT COMMENT '뮤지컬게시글ID',
    `title`            VARCHAR(1023) NOT NULL COMMENT '뮤지컬게시글제목',
    `content`          TEXT          NOT NULL COMMENT '뮤지컬게시글내용',
    `created_at`       TIMESTAMP     NOT NULL COMMENT '뮤지컬게시글 작성일자',
    `updated_at`       TIMESTAMP COMMENT '뮤지컬게시글 수정일자',
    `view_count`       BIGINT        NOT NULL COMMENT '뮤지컬게시글 조회수',
    `like`             BIGINT        NOT NULL COMMENT '뮤지컬게시글 좋아요',
    `comments`       BIGINT NOT NULL DEFAULT 0 COMMENT '댓글수',
    `active_status`    VARCHAR(255)  NOT NULL COMMENT '활성화 여부',
    `user_id`          BIGINT COMMENT '회원ID',
    `musical_id`       BIGINT COMMENT '뮤지컬ID',
    PRIMARY KEY (`musical_board_id`),
    CONSTRAINT fk_musical_board_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`),
    CONSTRAINT fk_musical_board_musical FOREIGN KEY (`musical_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`)
) COMMENT = '뮤지컬게시글';

CREATE TABLE `TBL_MUSICAL_BOARD_COMMENT`
(
    `musical_board_comment_id` BIGINT       NOT NULL AUTO_INCREMENT COMMENT '뮤지컬 댓글ID',
    `content`                  TEXT         NOT NULL COMMENT '내용',
    `created_at`               TIMESTAMP    NOT NULL COMMENT '작성시각',
    `updated_at`               TIMESTAMP COMMENT '수정시각',
    `active_status`            VARCHAR(255) NOT NULL COMMENT '활성화여부',
    `musical_board_id`         BIGINT COMMENT '뮤지컬게시글ID',
    `user_id`                  BIGINT COMMENT '회원ID',
    PRIMARY KEY (`musical_board_comment_id`),
    CONSTRAINT fk_musical_board_comment_board FOREIGN KEY (`musical_board_id`) REFERENCES `TBL_MUSICAL_BOARD` (`musical_board_id`),
    CONSTRAINT fk_musical_board_comment_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '뮤지컬 게시글 댓글';

CREATE TABLE `TBL_MUSICAL_BOARD_LIKE`
(
    `musical_board_id` BIGINT NOT NULL COMMENT '뮤지컬게시글ID',
    `user_id`          BIGINT NOT NULL COMMENT '회원ID',
    PRIMARY KEY (`musical_board_id`, `user_id`),
    CONSTRAINT fk_musical_board_like_board FOREIGN KEY (`musical_board_id`) REFERENCES `TBL_MUSICAL_BOARD` (`musical_board_id`),
    CONSTRAINT fk_musical_board_like_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '뮤지컬게시글좋아요';

CREATE TABLE `TBL_MUSICAL_BOARD_REPLY`
(
    `musical_board_reply_id`   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '뮤지컬 대댓글ID',
    `content`                  TEXT         NOT NULL COMMENT '내용',
    `created_at`               TIMESTAMP    NOT NULL COMMENT '생성시각',
    `updated_at`               TIMESTAMP COMMENT '수정시각',
    `active_status`            VARCHAR(255) NOT NULL COMMENT '활성화상태',
    `musical_board_comment_id` BIGINT COMMENT '뮤지컬 댓글ID',
    `user_id`                  BIGINT COMMENT '회원ID',
    PRIMARY KEY (`musical_board_reply_id`),
    CONSTRAINT fk_musical_reply_comment FOREIGN KEY (`musical_board_comment_id`) REFERENCES `TBL_MUSICAL_BOARD_COMMENT` (`musical_board_comment_id`),
    CONSTRAINT fk_musical_reply_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '뮤지컬대댓글';

CREATE TABLE `TBL_REVIEW_COMMENT`
(
    `review_comment_id` BIGINT       NOT NULL AUTO_INCREMENT COMMENT '리뷰댓글ID',
    `content`           TEXT         NOT NULL COMMENT '내용',
    `created_at`        TIMESTAMP    NOT NULL COMMENT '작성 시각',
    `updated_at`        TIMESTAMP COMMENT '수정 시각',
    `active_status`     VARCHAR(255) NOT NULL COMMENT '활성화 여부',
    `user_id`           BIGINT COMMENT '회원ID',
    `review_id`         BIGINT COMMENT '리뷰ID',
    PRIMARY KEY (`review_comment_id`),
    CONSTRAINT fk_review_comment_review FOREIGN KEY (`review_id`) REFERENCES `TBL_REVIEW` (`review_id`),
    CONSTRAINT fk_review_comment_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '리뷰 댓글';

CREATE TABLE `TBL_REVIEW_LIKE`
(
    `review_id` BIGINT NOT NULL COMMENT '리뷰ID',
    `user_id`   BIGINT NOT NULL COMMENT '회원ID',
    PRIMARY KEY (`review_id`, `user_id`),
    CONSTRAINT fk_review_like_review FOREIGN KEY (`review_id`) REFERENCES `TBL_REVIEW` (`review_id`),
    CONSTRAINT fk_review_like_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '리뷰좋아요';

CREATE TABLE `TBL_SECRET_REVIEW`
(
    `secret_review_id` BIGINT       NOT NULL AUTO_INCREMENT COMMENT '비밀리뷰ID',
    `content`          TEXT         NOT NULL COMMENT '내용',
    `created_at`       TIMESTAMP    NOT NULL COMMENT '작성 시각',
    `updated_at`       TIMESTAMP COMMENT '수정 시각',
    `active_status`    VARCHAR(255) NOT NULL COMMENT '활성화 여부',
    `musical_id`       BIGINT COMMENT '뮤지컬ID',
    `user_id`          BIGINT COMMENT '회원ID',
    PRIMARY KEY (`secret_review_id`),
    CONSTRAINT fk_secret_review_musical FOREIGN KEY (`musical_id`) REFERENCES `TBL_MUSICAL_INFO` (`musical_info_id`),
    CONSTRAINT fk_secret_review_user FOREIGN KEY (`user_id`) REFERENCES `TBL_USER` (`user_id`)
) COMMENT = '비밀리뷰';

CREATE TABLE TBL_PERFORMANCE_RANK (
                                      performance_rank_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '순위ID',
                                      start_date TIMESTAMP NOT NULL COMMENT '한달전날짜',
                                      end_date TIMESTAMP NOT NULL COMMENT '조회날짜',
                                      rank_type VARCHAR(255) NOT NULL COMMENT '순위유형',
                                      rank INT NOT NULL COMMENT '순위',
                                      musical_id BIGINT NOT NULL COMMENT '작품ID',
                                      performance_id BIGINT NOT NULL COMMENT '공연ID',
                                      FOREIGN KEY (musical_id) REFERENCES TBL_MUSICAL_INFO(musical_info_id),
                                      FOREIGN KEY (performance_id) REFERENCES TBL_PERFORMANCE(performance_id),
                                      UNIQUE (start_date, end_date, rank_type, rank)
);
