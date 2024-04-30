CREATE TABLE ARTICLES
(
    articleId    LONG AUTO_INCREMENT PRIMARY KEY,
    userId       VARCHAR(255),
    title        VARCHAR(255),
    content      VARCHAR(255),
    creationDate TIMESTAMP,
    pageViews    LONG,
    deleted      BOOLEAN, // 추가된 부분
    FOREIGN KEY (userId) REFERENCES USERS (userId)
);