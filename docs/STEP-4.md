스프링 카페 - 로그인 구현 [STEP-4]
===

# ✅ 구현 사항 체크리스트

## 1) 세션 기능 구현 
- [X] 세션을 통해 로그인 기능 구현
- [X] 세션 로그아웃 기능 구현

## 2) 세션에 따른 상단 메뉴 구현
- [X] 로그인 되지 않은 경우 : ```로그인```, ```회원가입```
- [X] 로그인 된 경우 : ```내 정보 보기```, ```정보 수정```, ```로그아웃```

## 3) 세션에 따른 접근 권한 구현
- [x] 로그인 된 경우 
  - ```/users/login``` : 로그인 페이지 접근 불가
  - ```/users/join``` : 회원가입 페이지 접근 불가
- [x] 로그인 되지 않은 경우
  - ```/users``` : 멤버 리스트 접근 불가
  - ```/articles/write``` : 게시글 작성 불가
  - ```/users/{userId}/form``` : 유저 수정 불가

## 4) 게시글 조회수 기능 구현
- [x] Article 객체 수정
    - 조회수를 가지도록 수정
- [x] Article Table 수정
  - 조회수를 가지도록 수정
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

## 5) 비밀번호 확인 기능 개선
- [x] User 객체를 생성할 때, 비밀번호에 대해 salt, hash 값을 가지도록 설정
```java
public User(String userId, String email, String name, String password) {
      this.userId = userId;
      this.email = email;
      this.name = name;
      byte[] saltBytes = generateSalt();
      this.salt = Base64.getEncoder().encodeToString(saltBytes);
      this.hashedPassword = hashPassword(password, saltBytes);
      this.creationDate = LocalDate.now(); // 현재 날짜를 사용하여 초기화
}
```
- [x] USERS table 구조 수정
```
CREATE TABLE USERS (
       userId VARCHAR(255) PRIMARY KEY,
       email VARCHAR(255),
       name VARCHAR(255),
       hashedPassword VARCHAR(255),
       creationDate VARCHAR(255)
);
```
- [x] User 비밀번호를 비교할 때, Salt 값을 가지고 hashing 하도록 설정
```java
public boolean verifyPassword(String inputPassword) {
    String hashedInputPassword = hashPassword(inputPassword, Base64.getDecoder().decode(salt));
    return hashedInputPassword.equals(hashedPassword);
}

private String hashPassword(String password, byte[] salt) {
    try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(salt);
        byte[] hashedBytes = messageDigest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
    }
}
```

## 6) Exception 처리 개선
- 현재 Exceptions
  - ```UserNotFoundException``` : 유저 정보를 찾지 못했을 때
  - ```PasswordMismatchException``` : 유저 비밀번호가 일치하지 않을 때
  - ```ArticleNotFoundException``` : 게시글 정보를 찾지 못할 때
- [ ] 예외 처리 응답이 모두 200이다. 이것을 404로 바꾼다


