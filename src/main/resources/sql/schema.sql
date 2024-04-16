-- 초기화
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS USERS;

-- USERS 테이블 생성
CREATE TABLE USERS(
    userId VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255)
);


-- article 테이블 생성
CREATE TABLE article(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    createdAt TIMESTAMP,
    authorId VARCHAR(255),
    POINT INT,
    STATUS VARCHAR(10) DEFAULT 'OPEN' CHECK (STATUS IN ('OPEN', 'CLOSE'))
);

-- comment 테이블 생성
CREATE TABLE comment(
    id INT AUTO_INCREMENT PRIMARY KEY,
    articleId INT,
    title VARCHAR(255),
    content TEXT,
    createdAt TIMESTAMP,
    authorId VARCHAR(255),
    STATUS VARCHAR(10) DEFAULT 'OPEN' CHECK (STATUS IN ('OPEN', 'CLOSE'))
);



ALTER TABLE USERS ADD CONSTRAINT unique_userid_name UNIQUE (USERID, NAME);
-- article 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE article ADD CONSTRAINT fk_authorId FOREIGN KEY (authorId) REFERENCES users (userId) ON DELETE CASCADE;

-- comment 테이블에 외래 키(FK) 및 ON DELETE CASCADE 설정
ALTER TABLE comment ADD CONSTRAINT fk_articleId FOREIGN KEY (articleId) REFERENCES article (id) ON DELETE CASCADE;
ALTER TABLE comment ADD CONSTRAINT fk_authorId_c FOREIGN KEY (authorId) REFERENCES users (userId) ON DELETE CASCADE;
