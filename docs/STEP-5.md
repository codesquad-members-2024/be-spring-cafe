스프링 카페 - 게시글 권한부여 [STEP-5]
===

# ✅ 구현 사항 체크리스트

## 1) 로그인 여부에 따른 권한 부여

- [x] 게시글 목록 접근 권한
    - 로그인 ⭕️ : 게시글 목록 보기 가능
    - 로그인 ❌ : 게시글 목록 보기 가능
- [x] 게시글 세부 내용 접근 권한
    - 로그인 ⭕️ : 게시글 목록 세부 내용 접근 가능
    - 로그인 ❌ : 게시글 목록 세부 내용 접근 불가
- [x] 게시글 작성
    - 로그인 ⭕️ : 게시글 작성 가능
    - 로그인 ❌ : 게시글 작성 불가
- [ ] 게시글 수정
    - 로그인 ⭕️
        - 자신의 게시글 : 수정 가능
        - 타인 게시글 : 수정 불가
    - 로그인 ❌ : 게시글 수정 불가

## 2) 게시글 작성하기

- [x] 글작성 화면에서 글쓴이 입력 필드 삭제
- [x] 로그인 하지 않은 사용자가 글쓰기 페이지에 접근할 경우, 로그인 페이지로 이동

## 3) 게시글 수정하기 ```PUT MAPPING```

- [ ] 수정하기 폼, 기능은 로그인 사용자 == 글쓴이 아이디 가 같은 경우에 가능
- [ ] "다른 사람의 글을 수정할 수 없다" 라는 에러 페이지 구현
    - 권한에 따른 응답이므로, 403 메세지

## 4) 게시글 삭제하기 ```DELETE MAPPING```

- [ ] 로그인 사용자와 세션 사용자가 같은 경우에만 삭제가 가능하다
- [ ] 다른 사람의 글을 삭제할 수 없다 라는 에러 페이지 구현
    - 권한에 따른 응답이므로, 403 메세지

## 5) 사용자 수정하기 
- [ ] 세션 사용자 id와 사용자 id가 같은 경우에만 수정 가능

---

# 🛠️ 구현 사항 리팩토링

## 1) ```Dto```에 대한 역할을 부여하기

- 기존 코드에서는, Model 외에는 모두 ```Dto```라는 이름을 붙여서 진행하였다.
    - 그러다 보니, 가독성 측면에서나 역할의 이해 측면에서나 ```Dto```라는 것에 종속되어 움직인다고 느껴졌다.
- 또한, ```Dto```에 대한 개념이 헷갈리다 보니, 어떤 부분에서 사용해야하는지에 혼동을 느꼈던 것 같다.
    - ```Dto```란, 데이터베이스의 레코드를 대표한다고 생각하면 리팩토링을 하기 쉬울 것 같다!

### 해결

- 따라서, ```Dto```라는 이름 보다는 ```하는 역할```에 집중하고자 하였다.
    - ```ArticleCreateDto``` 를 ```ArticleCreationRequest```로 변경
    - ```UserCreateDto```를 ```UserCreationRequest```
    - ```UserLoginDto``` -> ```UserLoginRequest```
- User 검증에 대한 객체는 Service 단에 있어야 할 것 같..?
    - ```UserCredentialDto``` -> ```UserCredentialData```

## 2) Salt 값 없애기

- 동일한 비밀번호에 대해서도 다른 해시값을 만들어내기 위해서 ```Salt```값을 추가
    - 결국엔 ```Salt```값도 저장해주어야 하는 값인데, 이를 ```User``` 테이블에 저장하면 데이터 구조가 불명확해지는 단점이 생긴다.

### 해결

- 단순히 Hash 값만 일단 돌리도록 개선..
- CREATE USERS TABLE

```
CREATE TABLE USERS (
       userId VARCHAR(255) PRIMARY KEY,
       email VARCHAR(255),
       name VARCHAR(255),
       hashedPassword VARCHAR(255),
       creationDate VARCHAR(255)
);
```

- CREATE ARTICLES TABLE

```
CREATE TABLE ARTICLES (
      articleId LONG AUTO_INCREMENT PRIMARY KEY,
      userId VARCHAR(255),
      title VARCHAR(255),
      content VARCHAR(255),
      creationDate VARCHAR(255), -- creationDate를 DATE 타입으로 변경
      pageViews LONG,
      FOREIGN KEY (userId) REFERENCES USERS(userId)
);
```

## 3) ```users/login``` & ```users/{userId}``` 접속 문제

- URL을 해당 경로로 해놓았더니, user가 회원가입을 할 때, userId가 ```login```인 경우에는 유저 페이지 접근이 불가하였다.
- 이를 통해, URL에 문제점이 있다는 것을 알게 되었고, 리팩토링!

### 해결

| URL                         | 기능                      | 구현 상태 |
|:----------------------------|:------------------------|:-----:|
| GET /users/profile/{userId} | 유저 아이디에 대한 프로필을 보여준다    |  ⭕️   |
| GET /users/update/{userId}  | 유저 아이디에 대한 수정 페이지를 보여준다 |  ⭕️   |
| PUT /users/update/{userId}  | 유저 아이디 수정 기능            |  ⭕️   |