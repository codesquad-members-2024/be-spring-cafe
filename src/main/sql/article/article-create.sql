create table article
(
    identifier VARCHAR(255) PRIMARY KEY NOT NULL,
    writer  VARCHAR(255) NOT NULL,
    title   VARCHAR(255) NOT NULL,
    contents TEXT         NOT NULL,
    createTime TIMESTAMP    NOT NULL,
    FOREIGN KEY (writer) REFERENCES USERS (NAME)
);