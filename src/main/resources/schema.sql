-- article 외래키 제약조건 먼저 제거
alter table if exists article drop constraint if exists fk_created_by;

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
    constraint fk_created_by foreign key (created_by) references member(login_id) on delete cascade
);
