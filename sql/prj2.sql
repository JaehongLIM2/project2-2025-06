CREATE TABLE board
(
    id      INT AUTO_INCREMENT NOT NULL,
    title   VARCHAR(100)       NOT NULL,
    content VARCHAR(1000)      NULL,
    writer  VARCHAR(50)        NOT NULL,
    created datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id)
);

DROP TABLE board;

CREATE TABLE board
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    title   VARCHAR(100)          NOT NULL,
    content VARCHAR(1000)         NULL     DEFAULT '내용 없음',
    writer  VARCHAR(50)           NOT NULL,
    created datetime              NOT NULL DEFAULT NOW(),
    views   BIGINT                NOT NULL,
    CONSTRAINT pk_board PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       VARCHAR(50)  NOT NULL,
    password VARCHAR(50)  NOT NULL,
    nickname VARCHAR(50)  NOT NULL UNIQUE,
    email    VARCHAR(100) NULL,
    phone    VARCHAR(30)  NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

DROP TABLE user;

ALTER TABLE board
    ADD FOREIGN KEY (writer) REFERENCES user (id)
        ON DELETE CASCADE;

# 이미지 컬럼 추가
ALTER TABLE user
    ADD COLUMN profile_image VARCHAR(255);
