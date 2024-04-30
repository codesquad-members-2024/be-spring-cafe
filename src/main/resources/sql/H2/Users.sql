CREATE TABLE USERS
(
    userId         VARCHAR(255) PRIMARY KEY,
    email          VARCHAR(255),
    name           VARCHAR(255),
    hashedPassword VARCHAR(255),
    creationDate   TIMESTAMP
);