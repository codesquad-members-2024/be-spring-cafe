# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-7 ajax 댓글 구현

### ⚒ 기능 구현 목록

- [x] ajax로 댓글 추가
- [x] ajax로 댓글 삭제
- [x] 데이터베이스 mysql로 변경

### ✍️ 메소드 convention

| url                                   | 기능                              |
|---------------------------------------|---------------------------------|
| GET /user/register                    | 회원가입 화면 제공                      |
| POST /user/register                   | 새로운 유저 회원가입 진행                  |
| GET /user/list                        | 전체 회원 리스트 화면 제공                 |
| GET /user/profile/:userId             | userId에 해당되는 유저의 프로필 화면 제공      |
| GET /article                          | 게시글을 작성할 수 있는 작성 폼 화면 제공        |
| POST /article                         | 새로운 게시글 등록                      |
| GET /article/:articleId               | articleId에 해당되는 질문의 상세 내용 화면 제공 |
| GET /user/profile/:userId/update      | 회원정보 수정 폼 화면 제공                 |
| PUT /user/profile/:userId/update      | 회원정보 수정 요청                      |
| GET /article/edit/:articleId          | 게시글 수정 폼 화면 제공                  |
| PUT /article/edit/:articleId          | 게시글 수정                          |
| DELETE /article/delete/:articleId     | 게시글 삭제                          |
| GET /login                            | 로그인 화면 제공                       |
| POST /login                           | 로그인 진행                          |
| POST /logout                          | 로그아웃 진행                         |
| GET /                                 | 홈 화면 - 전체 글 목록 표기               |
| POST /api/reply/:articleId            | 댓글 달기                           |
| DELETE /api/reply/:articleId/:replyId | 댓글 삭제                           |
| GET /reply/:articleId/:replyId        | 댓글 수정 화면 제공                     |
| PUT /reply/:articleId/:replyId        | 댓글 수정                           |

### 🤔 설계 및 고민

#### - 동적으로 추가하는 곳에 article.id의 값을 사용할 수 없다.

- 최초로 랜더링에서 사용된 모델의 values(article/replies)는 ajax로 새로운 html을 추가하는 경우에는 다시 사용할 수 없는 것 같다.
- 그렇기 때문에 ajax로 추가하는 부분을 위한 dto를 따로 생성해서 필요한 값을 다시 담아줘야 했다.
- 기존에는 DB에 댓글을 저장하면 어차피 댓글 목록을 보여줄 때는 리다이렉트로 넘어가기 때문에 메서드는 그냥 저장만 하고 아무런 반환값이 없었는데,
  ajax 방식으로 변경하니 방금 추가한 댓글에 관한 정보를 넘겨주어야 하는 상황이 되었다.
- 필요한 정보중 하나인 댓글의 PK는 DB에서 자동으로 만들어주는 long이기 때문에 이 값을 얻기 위해서는 저장과 동시에 PK를 가져와야 했다.
- 즉 기존 article을 저장했던 방식과 동일하게 SimpleJdbcInsert를 사용해서 구현하도록 하면 된다.

#### - 댓글 삭제 시 응답 방법 고민

- 처음에는 Result에 삭제 성공 실패 여부를 담아서 반환하는 것으로만 생각했는데, 이렇게 되니 실패 시 상태 응답코드를 제어할 수 없다는 것이 고민이었다.
- 그래서 body에 담을 값은 Result 객체로 표현하고, 그 외 응답 내용은 ResponseEntity를 활용하여 구현했다.

#### - jquery 클릭이벤트 동적으로 적용되지 않는 이유?

```
$(".delete-answer-button").click(deleteAnswer);
```

- click은 해당 스크립트가 실행될 때 존재하는 요소들에만 클릭이벤트를 바인딩하게 된다.
- 따라서 이후에 동적으로 추가되는 부분들에는 해당 이벤트가 연결되지 않는 것.

```
$(".qna-comment-slipp-articles").on("click", ".delete-answer-button", deleteAnswer);
```

- 대신 on을 사용하게 되면, 실제 클릭이 일어나는 버튼에 핸들러가 바인딩되는 것이 아니라 상위 클래스인 `qna-comment-slipp-articles`에 바인딩된다.
- `delete-answer-button`(selector)에 click이벤트가 일어나면 `qna-comment-slipp-articles`의 이벤트 핸들러가 작동해서 클릭이 일어난
  버튼을 식별하고 해당 버튼에서 handler가 작동하게 된다.
- 따라서 이벤트 핸들러가 직접 바인딩되어 있지 않은 동적으로 추가된 버튼이라도, 상위 클래스 요소가 핸들러를 실행해주기 때문에
  정상적으로 삭제가 되는 것!
- 이를 이벤트의 위임이라고 한다.

> When a selector is provided, the event handler is referred to as delegated.
> The handler is not called when the event occurs directly on the bound element,
> but only for descendants (inner elements) that match the selector.
> jQuery bubbles the event from the event target up to the element where the handler is attached
> (i.e., innermost to outermost element) and runs the handler for any elements along that path matching the selector.
> Event handlers are bound only to the currently selected elements; they must exist at the time your code makes the call
> to .on().

### 📚 학습 중

#### @RequestBody

- @RequestBody 어노테이션을 붙여 JSON으로 들어오는 요청 body를 해당 객체로 바인딩 할 수 있게 해준다.
- HttpMessageConverter를 사용해서 JSON/XML등의 형태를 JAVA 객체로 적절하게 변환해준다.
- ResponseBody와는 반대의 역할

#### AJAX

- Asynchronous JavaScript and XML
- 자바스크립트와 XML을 이용해 비동기적으로 정보를 주고받는 개발 기법
- AJAX를 활용해서 XHR(XML HTTP REQUEST)로 통신할때 요즘은 주로 JSON 형식으로 응답을 주고받는다

#### Jquery

> jQuery is a fast, small, and feature-rich JavaScript library. It makes things like HTML document traversal and
> manipulation,
> event handling, animation, and Ajax much simpler with an easy-to-use API that works across a multitude of browsers.

#### RestController

- @ResponseBody + @Controller 의 역할을 하고 있는 편의 어노테이션
- 기존 뷰네임을 반환했던 것과 다르게
- 필요한 곳마다 @ResponseBody를 붙이지 않아도 되고, 일관적으로 해당 컨트롤러가 api 요청을 처리한다는 의미 전달 가능