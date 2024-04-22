ALTER TABLE IF EXISTS ARTICLE
    DROP constraint IF EXISTS FK_ARTICLE_USER;
ALTER TABLE IF EXISTS REPLY
    DROP constraint IF EXISTS FK_REPLY_USER;
ALTER TABLE IF EXISTS REPLY
    DROP constraint IF EXISTS FK_REPLY_ARTICLE;

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
    articleId   INT PRIMARY KEY AUTO_INCREMENT,
    author      VARCHAR(100) NOT NULL,
    title       VARCHAR(100) NOT NULL,
    contents    TEXT         NOT NULL,
    userId      VARCHAR(100) NOT NULL,
    createdTime TIMESTAMP,
    deleted     BOOLEAN DEFAULT FALSE
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
    add constraint fk_article_user foreign key (userId) references USERS (userId);
alter table REPLY
    add constraint fk_reply_user foreign key (userId) references USERS (userId);
alter table REPLY
    add constraint fk_reply_article foreign key (articleId) references ARTICLE (articleId);