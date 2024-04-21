-- H2 Dialect
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       loginId VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE question (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          userId BIGINT NOT NULL,
                          FOREIGN KEY (userId) REFERENCES users(id),
                          title VARCHAR(255) NOT NULL,
                          content LONGTEXT NOT NULL,
                          viewCnt INTEGER NOT NULL DEFAULT 0,
                          createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);