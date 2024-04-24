 drop table if exists `user` CASCADE;
 create table `user`
 (
     userId    varchar(50),
     password  varchar(100),
     name      varchar(50),
     email     varchar(50),
     primary key (userId)
 );

 drop table if exists `article` CASCADE;
 create table `article`
 (
     id        BIGINT,
     timestamp TIMESTAMP,
     writer    VARCHAR(50),
     title     VARCHAR(50),
     content   TEXT,
     primary key (id),
     foreign key (writer) references `user`(userId)
 );