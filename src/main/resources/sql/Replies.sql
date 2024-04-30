CREATE TABLE REPLIES
(
    replyId      LONG AUTO_INCREMENT PRIMARY KEY,
    articleId    LONG,
    userId       VARCHAR(255),
    comment      VARCHAR(255),
    creationDate VARCHAR(255), -- creationDate를 DATE 타입으로 변경
    deleted      BOOLEAN,      // 추가된 부분
    FOREIGN KEY (userId) REFERENCES USERS (userId),
    FOREIGN KEY (articleId) REFERENCES ARTICLES (articleId)
);