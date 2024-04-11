create table article
(
    article_id bigint primary key auto_increment,
    title      varchar(255),
    contents   longvarchar,
    created_by varchar(255),
    created_at timestamp
)