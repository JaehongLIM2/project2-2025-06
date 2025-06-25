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
    title   VARCHAR(255)          NOT NULL,
    content VARCHAR(255)          NULL     DEFAULT '내용 없음',
    writer  VARCHAR(255)          NOT NULL,
    created datetime              NOT NULL DEFAULT NOW(),
    views   BIGINT                NOT NULL,
    CONSTRAINT pk_board PRIMARY KEY (id)
);