# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-5 게시글 권한부여

### ⚒ 기능 구현 목록

- [x] 로그인한 사용자는 게시글 상세보기 화면에서 댓글 확인이 가능하도록 하는 기능
- [x] 로그인 사용자가 댓글을 등록할 수 있는 기능
- [x] 자신이 작성한 댓글을 삭제할 수 있는 기능
- [x] 게시글 삭제 로직 변경
  - [x] 댓글이 없을 때만 삭제 가능
  - [x] 글 작성자가 쓴 댓글만 있는 경우에는 댓글이 있어도 삭제 가능
  - [x] 게시글을 삭제할 때 댓글도 삭제되어야 한다.
- [x] 다른 사용자가 작성한 댓글은 수정 삭제 버튼 안보이도록 하는 기능
- [x] 자신이 작성한 댓글을 수정할 수 있는 기능

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
| POST /reply/:articleId            | 댓글 달기                           |
| DELETE /reply/:articleId/:replyId | 댓글 삭제                           |
| GET /reply/:articleId/:replyId    | 댓글 수정 화면 제공                     |
| PUT /reply/:articleId/:replyId    | 댓글 수정                           |

### 🤔 설계 및 고민
#### - 삭제되지 않은 댓글 & 게시글만 화면에 표시하는 법
- 댓글을 soft delete로 변경했기 때문에 삭제를 했더라도 DB에 댓글 레코드는 남아있다.
- 그렇기 때문에 article에 달린 댓글을 다 가져와서 화면에 표시할 때, 이전과 달리 해당 댓글은 보이지 않도록 하는 처리가 필요하다.
- 처음에는 전체 댓글을 가져오고 html 파일에서 thymeleaf if 기능을 이용해 deleted 컬럼이 false인 레코드들만 보여주는 것으로 생각했다.
- 하지만 이럴 경우 모델에 해당 값을 담아주고, html 파일에서 표시를 위한 부분을 추가 작성해야 하는 등의 추가적인 작업이 필요할뿐더러
굳이 필요없는 데이터를 화면에 보낼 필요는 없을 것 같았다.
- 그래서 결론적으로는 DB에서 가져온 값을 deleted = ture 인 댓글은 service에서 미리 걸러서 모델로 보내도록 했다.

### 📚 학습 중
#### - H2 DB의 auto increment 초기화
- sql -> ALTER TABLE [TABLE명] AUTO_INCREMENT = [시작할 값];
- h2 -> ALTER TABLE 테이블명 ALTER COLUMN 컬럼명 RESTART WITH 1

### 🎡 실수
#### - DB에 값을 넣을 때 default 값을 설정해주었음에도 불구하고 계속 null값이 들어가는 현상
- 문제상황
  - 테이블에 edited, deleted 컬럼은 default로 false값이 설정되어 있었다.
  - 그런데 데이터를 insert 하면 해당 컬럼이 false이 아닌 null이 된다.
- 원인
  - SimpleJdbcInsert를 사용하는 경우에는 parameters Map을 사용하는데, 여기에 아무것도 명시해주지 않을 경우
  default값이 설정되어 있더라도 null 값이 들어간다고 한다.
- 이유
  - 디버깅해서 따라가봤는데 확인해 봐도 잘 모르겠다 😂
  - 내부적으로 쿼리문을 만들어서 Map의 값으로 insert 하는 것인데 왜 콘솔창에서 insert 하는 것과 다를까?
