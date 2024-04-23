CREATE TABLE IF NOT EXISTS main.users (
    userid varchar(40) primary key ,
    password varchar(60),
    name varchar(40),
    email varchar(60)
) ;

CREATE TABLE IF NOT EXISTS main.articles (
    articleId int auto_increment primary key ,
    writer varchar(40),
    title varchar(255),
    content varchar(65535),
    createdtime timestamp,
    foreign key (writer) references users(userid)
)