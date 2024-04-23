-- H2 Dialect
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       loginId VARCHAR(255) UNIQUE,
                       email VARCHAR(255),
                       name VARCHAR(255),
                       password VARCHAR(255),
                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE question (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          userId BIGINT NOT NULL,
                          FOREIGN KEY (userId) REFERENCES users(id),
                          title VARCHAR(255),
                          content LONGTEXT,
                          viewCnt INTEGER NOT NULL DEFAULT 0,
                          modified BOOLEAN DEFAULT FALSE,
                          deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         userId BIGINT NOT NULL,
                         questionId BIGINT NOT NULL,
                         content TEXT,
                         modified BOOLEAN DEFAULT FALSE,
                         deleted BOOLEAN NOT NULL DEFAULT 0,
                         createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)