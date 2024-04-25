create table users
(
    user_id     VARCHAR(255) PRIMARY KEY NOT NULL,
    nickname    VARCHAR(255)             NOT NULL,
    email       VARCHAR(255)             NOT NULL,
    password    VARCHAR(255)             NOT NULL,
    createdDate TIMESTAMP                NOT NULL
);