스프링 카페 - 댓글 기능 구현 [STEP-6]
===

# ✅ 구현 사항 체크리스트

## 1) 댓글을 담당하는 클래스 ```Reply``` 클래스 구현

- [X] ```Reply``` 테이블 생성
    - ```replyId``` : AutoIncrement로 구현 **[PK]**
    - ```articleId``` : ```Articles``` 테이블의 ```articleId [PK]```를 가진다 **[FK]**
    - ```userId``` : ```Users``` 테이블의 ```userId [PK]``` 를 가진다 **[FK]**
    - ```content``` : 댓글 내용을 담고 있는 필드
    - ```creadtionDate``` : 댓글이 작성된 시간을 담고 있는 필드

```
CREATE TABLE REPLIES (
    replyId      LONG AUTO_INCREMENT PRIMARY KEY,
    articleId    LONG,
    userId       VARCHAR(255),
    comment      VARCHAR(255),
    creationDate VARCHAR(255), -- creationDate를 DATE 타입으로 변경
    FOREIGN KEY (userId) REFERENCES USERS (userId),
    FOREIGN KEY (articleId) REFERENCES ARTICLES (articleId)
);
```

![img.png](img/img_5.png)


## 2) 게시글 접속 시, 해당 게시물에 연결되어있는 모든 댓글들을 보이게 구현

- [ ] ```Articles```테이블의 ```articleId```에 종속되어있는 모든 ```Reply``` 레코드들을 보여주게 구현
    - 작성자 ```userId```가 보여야 한다
    - 작성 내용 ```content```가 보여야 한다
    - 작성 시간 ```creationDate```가 보여야 한다

## 3) 게시글 접속 시, 댓글 작성 폼에 ```작성자 id```를 보여준다.

- [ ] 댓글을 작성할 떄에는, 내용만 작성할 수 있다.
    - 작성자 란은 ```sessionedUser```값의 ```userId``` 필드를 이용한다.

---
