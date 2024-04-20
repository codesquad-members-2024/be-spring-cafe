drop table if exists `user` CASCADE;
create table `user`
(
    id        varchar(15),
    password  varchar(20),
    name      varchar(20),
    email     varchar(20),
    primary key (id)
);

drop table if exists `article` CASCADE;
create table `article`
(
    index     BIGINT,
    timestamp TIMESTAMP,
    writer    VARCHAR(20),
    title     VARCHAR(50),
    content   TEXT,
    primary key (index)
);