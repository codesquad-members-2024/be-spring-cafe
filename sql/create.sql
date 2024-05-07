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
CREATE TABLE files(
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      article_id BIGINT,
                      upload_file_name VARCHAR(255),
                      store_file_name VARCHAR(255),
                      FOREIGN KEY (article_id) REFERENCES articles(id)
)