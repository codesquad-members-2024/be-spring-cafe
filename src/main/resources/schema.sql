-- article
drop table if exists article;
create table article
(
    article_id bigint primary key auto_increment,
    title      varchar(255),
    contents   longvarchar,
    created_by varchar(255),
    created_at timestamp
);

-- member
drop table if exists member;
create table member
(
    member_id bigint primary key auto_increment,
    login_id  varchar(255),
    password  varchar(255),
    username  varchar(255),
    email     varchar(255)
);
