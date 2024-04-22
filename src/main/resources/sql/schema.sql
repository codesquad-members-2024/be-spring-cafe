DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    userId   VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL,
    deleted  BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS ARTICLE;
CREATE TABLE ARTICLE
(
    articleId INT PRIMARY KEY AUTO_INCREMENT,
    author    VARCHAR(100) NOT NULL,
    title     VARCHAR(100) NOT NULL,
    contents  TEXT         NOT NULL,
    userId    VARCHAR(100) NOT NULL,
    deleted   BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS REPLY;
CREATE TABLE REPLY
(
    replyId     INT PRIMARY KEY AUTO_INCREMENT,
    articleId   INT          NOT NULL,
    author      VARCHAR(100) NOT NULL,
    contents    TEXT         NOT NULL,
    userId      VARCHAR(100) NOT NULL,
    createdTime TIMESTAMP,
    deleted     BOOLEAN DEFAULT FALSE
);

alter table ARTICLE
    add foreign key (userId) references USERS (userId);
alter table REPLY
    add foreign key (userId) references USERS (userId);
alter table REPLY
    add foreign key (articleId) references ARTICLE (articleId);