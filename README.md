# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페

---

# 📜 URL Convention

| URL                | 기능                         | 구현 상태 |
|:-------------------|:---------------------------|:-----:|
| GET /users         | 회원가입을 할 수 있는 폼을 보여준다       |  ⭕️   |
| POST /users        | 입력된 폼을 가지고, 회원 가입을 수행한다    |  ⭕️   |
| GET /users/list    | 회원가입된 유저들을 보여준다.           |  ⭕️   |
| GET /users/:userId | useeId에 해당하는 profile을 보여준다 |   ⭕️    |

---

# 프로그램 동작

## 회원가입
### 1) ```localhost:8080/users``` 로 접속
![img.png](readme/img.png)

### 2) 회원가입 폼 작성 후, 회원가입 성공 화면
![img.png](readme/img1.png)

### 3) 회원가입한 유저들 목록 확인
![img.png](readme/img2.png)

### 4) 유저 상세 정보 확인
![img.png](readme/img4.png)