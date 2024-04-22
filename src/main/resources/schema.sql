-- article 외래키 제약조건 먼저 제거
SET foreign_key_checks = 0;
alter table article drop foreign key fk_created_by;
alter table comment drop foreign key fk_comment_created_by;
alter table comment drop foreign key fk_comment_article_id;
SET foreign_key_checks = 1;

-- member
drop table if exists member;
create table member
(
    member_id bigint primary key auto_increment,
    login_id  varchar(255) unique,
    password  varchar(255),
    username  varchar(255),
    email     varchar(255)
);

-- article
drop table if exists article;
create table article
(
    article_id bigint primary key auto_increment,
    title      varchar(255),
    contents   varchar(1000),
    created_by varchar(255),
    created_at timestamp,
    deleted boolean default false,
    constraint fk_created_by foreign key (created_by) references member(login_id) on delete cascade
);

-- comment
drop table if exists comment;
create table comment
(
    comment_id   bigint primary key auto_increment,
    article_id bigint,
    content   varchar(255),
    created_by varchar(255),
    created_at timestamp,
    deleted boolean default false,
    constraint fk_comment_created_by foreign key (created_by) references member(login_id),
    constraint fk_comment_article_id foreign key (article_id) references article(article_id) on delete restrict
);
