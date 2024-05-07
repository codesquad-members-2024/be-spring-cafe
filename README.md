# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

---

# 📜 URL Convention

| URL                                                | 기능                             | 구현 상태 |
|:---------------------------------------------------|:-------------------------------|:-----:|
| GET / , GET /main                                  | 등록된 모든 게시글들을 보여준다              |  ⭕️   |
| GET /users                                         | 회원가입된 유저들을 보여준다.               |  ⭕️   |
| POST /users                                        | 입력된 폼을 가지고, 회원 가입을 수행한다        |  ⭕️   |
| GET /users/join                                    | 유저 회원가입 폼을 보여준다                |  ⭕️   |
| GET /users/login                                   | 로그인 페이지를 보여준다                  |  ⭕️   |
| POST /users/login                                  | 사용자 로그인 기능을 수행한다               |  ⭕️   |
| POST /users/logout                                 | 로그인 된 사용자를 로그아웃                |  ⭕️   |
| GET /users/profile/{{userId}}                      | userId에 해당하는 profile을 보여준다     |  ⭕️   |
| GET /users/update/{{userId}}                       | userId에 해당하는 수정 페이지를 보여준다      |  ⭕️   | 
| PUT /users/{{userId}}                              | 사용자의 정보를 업데이트                  |  ⭕️   |
| GET /articles/write                                | 게시물 입력하는 폼을 보여준다               |  ⭕️   |
| POST /articles                                     | 입력한 폼을 POST 요청으로 보낸다           |  ⭕️   |
| GET /articles/{{articleId}}                        | articleId에 해당하는 게시물 상세정보를 보여준다 |  ⭕️   |
| GET /articles/update/{{articleId}}                 | articleId에 해당하는 수정 페이지를 보여줌    |  ⭕️   |
| PUT /articles/{{articleId}}                        | articleId의 제목과 내용을 수정          |  ⭕️   |
| DELETE /articles/{{articleId}}                     | articleId에 대한 게시글을 삭제          |  ⭕️   |
| POST /api/articles/{articleId}/replies             | articleId에 대한 댓글 작성            |  ⭕️   |
| DELETE /api/articles/{articleId}/replies/{replyId} | replyId에 대한 댓글을 삭제             |  ⭕️   |

---

# 프로그램 동작

## 회원가입

### 1) ```localhost:8080/users/join```로 접속

![img_1.png](readme/user/img_1.png)

### 2) 회원가입된 목록 조회

![img_2.png](readme/user/img_2.png)

---

## 회원 정보 상세 페이지

### 1) 유저 아이디를 클릭하여 접속

![img_3.png](readme/user/img_3.png)

### 2) 존재하지 않는 유저 경로로 접속했을 경우

![img_4.png](readme/user/img_4.png)
> ![img_5.png](readme/user/img_5.png)

---

## 회원 정보 수정

### 1) 수정하기 버튼을 클릭

![img_6.png](readme/user/img_6.png)

### 2) 정보들을 입력하여 수정

- 아이디는 수정되지 않음
  ![img_7.png](readme/user/img_7.png)

### 3) 등록된 유저 정보와 다른 비밀번호를 입력했을 경우

![img_8.png](readme/user/img_8.png)
> ![img_9.png](readme/user/img_9.png)

- 기존 PW : 123
- 수정 페이지에서 입력한 PW : 1234

> ![img_10.png](readme/user/img_10.png)

- 에러 로그가 뜨는 것을 볼 수 있다.

### 4) 성공적으로 업데이트

![img_11.png](readme/user/img_11.png)
> ![img_12.png](readme/user/img_12.png)
---

## 글쓰기

### 1) ```localhost:8080/articles```로 접속

![img.png](readme/article/img5.png)

### 2) 글 작성 후, 메인 화면 리다이렉션

- 최신 작성한 글이 가장 상단에 보인다

![img_1.png](readme/article/img6.png)

### 3) 제목을 눌러 상세 페이지 확인

- 제목, 내용, 작성자, 작성 시간 표시
    - 작성자 : default로 "작성자" 가 들어간다.
        - 로그인 기능 구현 후 수정 예정

![img_2.png](readme/article/img7.png)

---

## 댓글 쓰기

### 1) 자신의 글에 댓글 작성

- 자신이 작성한 댓글에 대해서는 수정 / 삭제 버튼이 생긴다
  ![img_3.png](docs/img1/img_3.png)

### 2) 자신의 글에 다른 사람이 댓글 작성

- 타인이 작성한 댓글은 수정 / 삭제 할 수 없다
  ![img_4.png](docs/img1/img_4.png)

## 게시글 삭제

## 1) 게시글에 댓글이 없는 경우, 자신의 댓글만 있는 경우

![img_5.png](docs/img1/img_5.png)
![img_6.png](docs/img/img_6.png)
![img_8.png](docs/img/img_8.png)

## 2) 게시글에 다른 사용자의 댓글이 있는 경우

- 삭제가 불가능하다
  ![img_9.png](docs/img/img_9.png)

---

# EC2 인스턴스에서 서버 동작

> http://3.34.194.184:8080/
>   - 인스턴스 종료 [04.15]

![img.png](readme/img.png)

## 에러 페이지

### 1) 없는 게시물을 조회하려고 할 때

> 3000번째 게시물은 없는 상태

![img.png](docs/img1/img.png)

### 2) 없는 사용자를 조회하려고 할 때

> whoami 라는 사용자는 없는 상태

![img_1.png](docs/img1/img_1.png)



---

# 구현한 기능

## Mustache를 사용하여 중복 html 제거

- ```templates```에 있는 html 파일들의 nav 부분이 중복된다.
    - 또한, 추후에 로그인이 되었을 경우와 안되었을 경우를 분리하기 위해 ```mustache 부분 템플릿```이 필요
- ```/templates/base``` 폴더에 html 파일을 생성
    - ```application.properties```에 suffix를 .html로 해놓았기 때문에, base 폴더의 부분 템플릿 확장자는 ```.html```로 구현

### mustache 부분 템플릿 사용법

- ```navBarNav.html```

```html

<ul class="nav navbar-nav navbar-right">
    <li class="active"><a href="/users/list">멤버 리스트</a></li>
    <li><a class="black-component" href="/users/login" role="button">로그인</a></li>
    <li><a class="black-component" href="/users" role="button">회원가입</a></li>
</ul>
```

- 위의 내비바 html을 적용하고자 하는 html에 주입한다.

- ```form.html```

```html

<nav class="navbar navbar-fixed-top header">
    <div class="col-md-12">
        {{> /base/navBarHeader}}
        {{> /base/navBarNav}} <!--해당 부분-->
    </div>
</nav>
```

- {{> 파일경로/파일이름}} 을 통해 html 요소를 넣어주어 html 중복 분리

---

## Custom Exception 구현

### 사용자를 못찾은 경우 : ```UserNotFoundException```

- UserManagementService에서 예외를 던진다.
    - 예외를 처리하는 ```ExceptionController```를 구현
        - ```/error/ErrorController```

### 사용자의 비밀번호가 틀린 경우 : ```PasswordMisMatchException```

### 게시글 삭제를 할 수 없는 경우 : ```ArticleAccessException```

### 게시글을 찾을 수 없는 경우 : ```ArticleNotFoundException```

### 댓글을 찾을 수 없는 경우 : ```ReplyNotFoundException```

### 사용자를 수정할 권한이 없는 경우 : ```UserAccessException```

---

## DB 모델링

![img_2.png](docs/img1/img_2.png)

---

## Password Hash

- 사용자 비밀번호를 평문으로 저장하지 않기 위해 구현
- sha256 해시 알고리즘을 이용하여 구현
- ```SHA256HashService```를 구현


