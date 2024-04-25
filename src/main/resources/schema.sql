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
    hits int default 0,
    isdeleted boolean default FALSE,
    foreign key (writer) references users(userid)
);

CREATE TABLE IF NOT EXISTS main.comments (
    commentId int auto_increment primary key,
    writer varchar(40),
    articleId int,
    content varchar(65535),
    createdtime timestamp,
    isdeleted boolean default FALSE,
    foreign key (writer) references users(userid),
    foreign key (articleId) references articles(articleId)
);