 drop table if exists `user` CASCADE;
 create table `user`
 (
     userId    varchar(255),
     password  varchar(255),
     name      varchar(255),
     email     varchar(255),
     primary key (userId)
 );

 drop table if exists `article` CASCADE;
 create table `article`
 (
     id        BIGINT,
     timestamp TIMESTAMP,
     writer    VARCHAR(255),
     title     VARCHAR(255),
     content   TEXT,
     primary key (id),
     foreign key (writer) references `user`(userId)
 );

 drop table if exists `reply` CASCADE;
 create table `reply`
 (
     articleId BIGINT not null,
     index BIGINT not null,
     writer VARCHAR(255) not null,
     timestamp TIMESTAMP not null,
     content TEXT not null,
     primary key (articleId, index),
     foreign key (articleId) references `article`(id),
     foreign key (writer) references `user`(userId)
 );

alter table `article` add deleted boolean default false;
