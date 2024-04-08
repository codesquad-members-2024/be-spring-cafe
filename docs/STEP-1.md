스프링 카페 - 회원 가입 및 목록 조회 기능 [Step-1]
===

# 구현 기능

## 1) WebRestController 생성

- ```url```에 해당하는 경로로 이동할 수 있도록 간단한 REST API 구현

## 2)

---

# ✅ 구현 사항 체크리스트

## 1) 웹 페이지 디자인

- [x] 기능들을 구현한 후, 디자인을 고려하자
    - 마크업 양식을 다운받아 해결

## 2) 회원가입 기능 구현

- [x] 가입하기 페이지에서 회원 가입 폼을 표시 ```/user/form.html```
- [x] 정보를 입력하고 확인을 누르면 POST 요청을 보내기 ```/users```
- [x] 회원 정보를 저장하기 ```/users```
- [x] redirect 하기 ```/users``` 요청은 'GET'
- [ ] ~~/users로 GET 요청을 보냈을 때, 회원가입한 회원들을 보여주기~~
    - 회원가입한 회원들을 보여주는 기능은 ```회원목록 조회 기능에서 구현!```

## 3) 회원 목록 조회 기능 구현

- [x] /users/list로 GET 요청을 처리한다
- [x] DB에서 회원 목록들을 가져와서, ```/user/list.html```에 전달한다.
- [x] /user/list.html 페이지에서 전달받은 회원 목록들을 보여준다.
  - Mustache 문법을 이용하여 구현

## 4) 회원 프로필 조회 기능 구현

- [ ] /user/list.html에서 profile 페이지로 들어갈 수 있도록 수정
- [ ] 경로를 /users/{userId}로 하여 GET 요청을 보낸다
- [ ] /users/{userId}로 전달된 GET 요청을 처리하여 ```/users/profile.html```로 보낸다
- [ ] /users/profile.html에서 유저 아이디에 해당하는 유저 정보를 확인한다.

---

# 📜 URL Convention

|         URL         |             기능             | 구현 상태 |
|:-------------------:|:--------------------------:|:-----:|
|     GET /users      |      저장된 회원 목록을 보여준다       |  ⭕️   |
|     POST /users     |  입력된 폼을 가지고, 회원 가입을 수행한다   |  ⭕️   |
|   GET /users/list   |      회원가입된 유저들을 보여준다.      |  ⭕️   |
| GET /users/:userdId | useeId에 해당하는 profile을 보여준다 |       |

---

# 🤯 마주친 오류

## 1) ```@RestController```가 적용되지 않음

- build.gradle 파일의 의존성에 web 의존성이 없었기 때문에 발생

### ⭕️ 해결

```
implementation 'org.springframework.boot:spring-boot-starter-web'
```

- build.gradle 파일에 web 의존성을 주입하여 해결

## 2) ```Mustache``` 적용

```java

@GetMapping("/users")
public String hello() {
    return "/user/form";
}
```

- 위 코드에서, /users로 접속했을 때, ```templates/user/form.html```이 불러와지지 않았다.

### ⭕️ 해결

- application.properties를 수정

```java
spring.application.name=spring-cafe

/* 인코딩 한글 깨짐 문제 해결*/
server.servlet.encoding.force=true

/* Mustache는 기본적으로 .mustache 파일로 동작. 이를 html로 바꾸어줌 */
spring.mustache.suffix=.html

spring.mustache.cache=false
```

- 한글 깨짐 문제를 해결
- suffix를 적용하여 html을 붙이도록 수정

---


