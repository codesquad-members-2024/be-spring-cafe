create table `user`
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id  VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created  TIMESTAMP    NOT NULL
);