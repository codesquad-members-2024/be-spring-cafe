-- article 외래키 제약조건 먼저 제거
alter table if exists article drop constraint if exists fk_created_by;
alter table if exists comment drop constraint if exists fk_comment_created_by;
alter table if exists comment drop constraint if exists fk_comment_article_id;

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
    contents   longvarchar,
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
