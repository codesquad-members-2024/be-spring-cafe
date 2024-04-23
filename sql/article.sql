CREATE TABLE articles
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer    VARCHAR(255) NOT NULL,
    title     VARCHAR(255) NOT NULL,
    content   TEXT         NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);