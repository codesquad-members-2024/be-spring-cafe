DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id bigint AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255),
                       name VARCHAR(255),
                       password VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);