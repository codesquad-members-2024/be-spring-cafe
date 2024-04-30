CREATE TABLE REPLIES
(
    replyId      BIGINT AUTO_INCREMENT PRIMARY KEY,
    articleId    BIGINT,
    userId       VARCHAR(255),
    comment      VARCHAR(255),
    creationDate TIMESTAMP,
    deleted      BOOLEAN, -- 추가된 부분
    FOREIGN KEY (userId) REFERENCES USERS (userId),
    FOREIGN KEY (articleId) REFERENCES ARTICLES (articleId)
);
