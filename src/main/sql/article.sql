create table article
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    writer  VARCHAR(255) NOT NULL,
    title   VARCHAR(255) NOT NULL,
    content TEXT         NOT NULL,
    created TIMESTAMP    NOT NULL,
    views   BIGINT       NOT NULL
);