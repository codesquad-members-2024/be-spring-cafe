CREATE TABLE ARTICLES
(
    articleId    BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId       VARCHAR(255),
    title        VARCHAR(255),
    content      VARCHAR(255),
    creationDate TIMESTAMP,
    pageViews    BIGINT,
    deleted      BOOLEAN,
    FOREIGN KEY (userId) REFERENCES USERS (userId)
);
