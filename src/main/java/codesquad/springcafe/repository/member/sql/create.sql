create table member
(
    member_id bigint primary key auto_increment,
    login_id  varchar(255),
    password  varchar(255),
    username  varchar(255),
    email     varchar(255)
)