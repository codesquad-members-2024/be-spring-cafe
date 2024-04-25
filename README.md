# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-5 게시글 권한부여

### ⚒ 기능 구현 목록

- [x] 로그인하지 않은 사용자는 게시글의 목록만 볼 수 있도록 제한
- [x] 로그인 한 사용자는 게시글의 세부 내용 볼 수 있도록 허용
- [x] 로그인 한 사용자만 게시글을 작성할 수 있도록 허용
- [x] 로그인 한 사용자는 자신의 게시글만 수정 및 삭제할 수 있도록 허용
- [x] 수정한 게시글은 edit으로 수정되었음을 표시
- [x] 타인의 게시글을 상세보기 하는 경우 수정, 삭제 버튼 보이지 않도록

### ✍️ 메소드 convention

| url                               | 기능                              |
|-----------------------------------|---------------------------------|
| GET /user/register                | 회원가입 화면 제공                      |
| POST /user/register               | 새로운 유저 회원가입 진행                  |
| GET /user/list                    | 전체 회원 리스트 화면 제공                 |
| GET /user/profile/:userId         | userId에 해당되는 유저의 프로필 화면 제공      |
| GET /article                      | 게시글을 작성할 수 있는 작성 폼 화면 제공        |
| POST /article                     | 새로운 게시글 등록                      |
| GET /article/:articleId           | articleId에 해당되는 질문의 상세 내용 화면 제공 |
| GET /user/profile/:userId/update  | 회원정보 수정 폼 화면 제공                 |
| PUT /user/profile/:userId/update  | 회원정보 수정 요청                      |
| GET /article/edit/:articleId      | 게시글 수정 폼 화면 제공                  |
| PUT /article/edit/:articleId      | 게시글 수정                          |
| DELETE /article/delete/:articleId | 게시글 삭제                          |
| GET /login                        | 로그인 화면 제공                       |
| POST /login                       | 로그인 진행                          |
| POST /logout                      | 로그아웃 진행                         |
| GET /                             | 홈 화면 - 전체 글 목록 표기               |

### 🤔 설계 및 고민

#### - 테이블간 관계 설정하기

- 로그인 한 사용자만 게시글을 작성할 수 있게 되면서 article 테이블과 user 테이블 사이의 관계 설정이 필요해졌다.
- 이를 위해 DB에서 article의 writer가 user의 userId를 가지도록 fk를 설정했다.
- 그런데 이를 자바 코드에 반영하는 과정에서 약간 혼란을 겪었음!
    - Article 클래스의 writer 멤버변수는 기존에 타입이 String이었는데, User 객체 자체로 바꿔버리게 된 것.
    - 그러다 보니 User 객체를 가져오기 위해 로직 상 ArticleRepository에서 UserRepository를 의존해야 하는 다소 이상한 상황이 발생했다.
    - 어떻게 하면 좋을지 고민하다 굳이 writer가 User일 필요는 없을 것 같았고, 그대로 String으로 유지하되 내용만 userId를 가지도록 했다.
    - 게다가 이렇게 하는 것이 DB의 컬럼 값과도 일치하니까 맞는 방향이라고 생각하게 됐다.

#### - 로그인 사용자 본인의 정보에 접근하는지 확인하는 validate 로직의 위치

- 글을 수정하는 기능에 있어서 타인의 글에 접근해서 제한을 걸어야 하는 경우, 요청 뿐 아니라 아예 폼을 보여주는 화면 자체도 접근하지 못하게 하고 싶다.
- 그래서 먼저 이를 체크하는 메서드 `validWriter`를 service에 작성했습니다.
- 그런데 폼을 보여줄 때는 `validWriter`를 컨트롤러에서 가져가서 사용하고, 게시글을 등록할 때는 service에 있는 등록 메서드를 호출하는데 이 메서드에서 다시 `validWriter`를 호출하고 있었다.
- 이러한 구조는 호출하는 레벨이 일관성이 없다는 생각이 들어서 해당 메서드를 호출하는 곳을 한군데로 통일하고 싶다는 생각이 들었다.
- 하지만 게시글을 보여주는 건 추가적인 service가 없으니까 컨트롤러에서 호출할 수 밖에 없는 상황이라고 느꼈다.
  &rarr; 결국 validator 클래스로 `validWriter`를 따로 분리해서 모두 컨트롤러 단에서 체크해주는 쪽으로 구현

### 📚 학습 중

#### 레코드 삭제 쿼리문

> DELETE FROM Articles WHERE id = articleId;

#### 기존 컬럼에 Foreign Key 연결하는 쿼리문

> ALTER TABLE ARTICLES ADD FOREIGN KEY (WRITER) REFERENCES USERS(USERID)

혹은 `CONSTRAINT 제약조건명` 이용하여 제약조건에 이름을 부여해줄 수도 있다.
> ALTER TABLE ARTICLES ADD CONSTRAINT 제약조건명 FOREIGN KEY (WRITER) REFERENCES USERS(USERID)

#### 제약 조건(Constraint)

- 테이블의 데이터 무결성을 보장하기 위해서 각종 조건들을 거는 것
  - Not Null
    - 컬럼 값에 null이 허용되지 않는다
  - Unique
    - 컬럼 값이 유니크함을 보장
    - null 허용
  - Check
    - 컬럼 값이 특정 조건 이내에 존재하도록 제한한다
  - Default
    - 데이터를 입력하지 않을 경우 기본 값이 세팅된다
  - Primary key
    - 컬럼 값을 pk로 만든다
  - Foreign key
    - 컬럼이 부모 테이블의 컬럼 값(PK,U)을 참조하도록 한다.
