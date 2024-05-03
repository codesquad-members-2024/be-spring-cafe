create table COMMENTS(
    identifier VARCHAR(255) PRIMARY KEY NOT NULL ,
    writer VARCHAR(255) not null ,
    writtenArticle VARCHAR(255) not null ,
    createTime TIMESTAMP not null ,
    contents TEXT not null ,
    likeCount INTEGER not null default 0,
    FOREIGN KEY (writer) REFERENCES USERS(NAME),
    FOREIGN KEY (writtenArticle) REFERENCES ARTICLE(IDENTIFIER)
)