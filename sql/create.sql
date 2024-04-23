create table users (
                       id bigint auto_increment primary key,
                       nickname varchar(255) unique key,
                       email varchar(255) unique key,
                       password varchar(255),
                       join_date date
)

create table articles (
                          id bigint auto_increment primary key,
                          writer varchar(255),
                          title varchar(255),
                          content text,
                          write_date timestamp,
                          views bigint,
                          is_deleted boolean,
                          foreign key (writer) references users (nickname)
)

create table comments (
                          id bigint auto_increment primary key,
                          writer varchar(255),
                          content text,
                          write_date timestamp,
                          article_id bigint,
                          is_deleted boolean,
                          foreign key (writer) references users (nickname)
)