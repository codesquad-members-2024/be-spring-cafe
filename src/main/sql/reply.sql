create table reply
(
    reply_id    BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    article_id  BIGINT       NOT NULL,
    writer      VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    createdDate TIMESTAMP    NOT NULL,
    FOREIGN KEY (article_id) REFERENCES article (article_id),
    FOREIGN KEY (writer) REFERENCES users (user_id)
);