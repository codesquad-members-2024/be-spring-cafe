# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-2 글쓰기 기능 구현

### ⚒ 기능 구현 목록

- [x] 게시글 작성 기능
- [x] 작성한 게시글을 조회하는 기능
- [x] 회원 정보 수정하는 기능

### ✍️ 메소드 convention

| url                               | 기능                              |
|-----------------------------------|---------------------------------|
| GET /user/register                | 회원가입 화면 제공                      |
| POST /user/register               | 새로운 유저 회원가입 진행                  |
| GET /user/list                    | 전체 회원 리스트 화면 제공                 |
| GET /user/profile/:userId         | userId에 해당되는 유저의 프로필 화면 제공      |
| GET /qna                          | 질문을 작성할 수 있는 작성 폼 화면 제공         |
| POST /qna                         | 새로운 질문 등록                       |
| GET /article/:articleId           | articleId에 해당되는 질문의 상세 내용 화면 제공 |
| GET /user/profile/:userId/update  | 회원정보 수정 폼 화면 제공                 |
| POST /user/profile/:userId/update | 회원정보 수정 요청                      |

### 🤔 설계 및 고민

#### - 타입이 String이 아닌 시간 객체를 화면에 표시하도록 하려면?

- Dates, temporals를 사용해서 자바의 날짜/시간 관련된 객체를 다룰 수 있다.
- 기존 Date 클래스는 Dates, 자바8 이후에 도입된 java.time 패키지를 사용하기 위해서는 temporlas를 사용하면 된다.

#### - 에러 핸들러 구현

- 존재하지 않는 질문 목록에 접근하려고 하는 경우 에러 응답을 하도록 구현하고 싶었다.

```java

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {IndexOutOfBoundsException.class, NoSuchElementException.class})
    private ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        String bodyOfResponse = "error while register article";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
```

- 전역에서 예외를 관리할 수 있도록 @ControllerAdvice를 사용했고, ResponseEntityExceptionHandler과 handleExceptionInternal를 통해 우선 구현했는데
  찾아보니 이는 ResponseEntity을 활용해서 세밀하게 지정해서 응답을 줄 수 있는 것에 장점이 있는 것으로 이해했다. (@ResponseBody로 직접 body 내용을 주는 느낌으로)
- 하지만 내가 지금 원하는 것은 단순히 특정 페이지로 리다이렉트하도록 응답을 보내는 것. 따라서 이 방법과는 다른 방법을 찾아보게 되었다.

```java

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = {IndexOutOfBoundsException.class, NoSuchElementException.class})
    private ModelAndView handleException() {
        ModelAndView modelAndView = new ModelAndView("exception/badrequest");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
```

- ModelAndView 객체를 이용해서 View로 응답할 수 있도록 했다. 이를 통해서 특정 예외가 들어왔을 때, 내가 원하는 페이지를 보여줄 수 있게 되었다.
- 사실 기본적으로 스프링부트에서 예외 처리를 해주는 부분이 있는데, 그래서 Whitelabel Error Page가 뜨는
  것! [링크](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-ann-rest-spring-mvc-exceptions)
- 이를 error/404.html 파일을 만들어서 해당 페이지를 보여주도록 임시로 설정해 두었다.

#### - 회원 정보의 수정을 위해 비밀번호를 비교하는 부분을 어디서 처리하는게 좋을까?

1. User객체에서 get으로 Password를 가져와서 처리한다  
&rarr; 비밀번호 내용을 외부에서 가져와서 처리하는 것이 안전하지 않다는 생각이 든다.
그치만 User는 데이터를 담는 구조 클래스라는 느낌이 강해서 안에 비교 로직을 넣는 것이 괜찮을까 하는 생각
2. User에게 검증할 password를 넘겨주고 기존 password와 비교하라고 시킨다  
&rarr; 하지만 그래도 해당 멤버변수를 가진 객체가 비교하는 것이 더 좋을 것 같아서 이렇게 구현하였다.

#### - 회원 정보를 수정할 때 어떤 식으로 User 객체의 값을 수정할 것인가?
- User에서 name, email, password 변수를 변경해야 함
- 이를 위해 멤버변수를 변경하기 위한 setter를 개별로 만들까를 고민했으나 로직 상 3개의 멤버변수가 항상 한꺼번에 변경되기 때문에 굳이 개별이 필요하지 않을 것이라고 생각하였다.  
&rarr; 하나의 메서드만으로 한꺼번에 변경하도록 구현
- but 기존 객체에서 수정 말고 아예 새롭게 User를 만들게 할 수도 있을 것 같다.

### 메서드 컨벤션 변경

- 메서드 url이 좀 더 구체적으로 기능을 나타낼 수 있도록 컨벤션을 변경해 보았다.
- API가 없는 단순 html으로 응답하는 경우 뒤에 파일명.html 이렇게 표기하도록 해야 할까 고민이 있었으나 일단 보류..!  
&rarr; 호눅스 리뷰 질문 관련

### 📚 학습 중

#### thymeleaf 문법

- 시간 포맷을 원하는 대로 표기하고 싶다  
  &rarr; `th:text="${#temporals.format(article.time, 'yyyy-MM-dd HH:mm')}"` temporals 이용해서 구현
- 사용자 정보를 바꿀 때, 비밀번호를 틀리는 경우 나머지 입력한 값은 유지하고 싶다  
  &rarr; `th:value="${userEdit != null ? userEdit.email : ''}"` 삼항연산자 이용해서 구현

#### 스프링 에러 처리 대략적 개요 [🔗](https://www.baeldung.com/exception-handling-for-rest-with-spring)

- @ExceptionHandler만을 사용하는 경우 문제 &rarr; 컨트롤러 내부에 어노테이션을 붙여서 에러 처리하는 메서드를 구현하게 되는데, 이는 해당 컨트롤러에서 발생하는 예외만 처리 가능함. 즉 전체
  어플리케이션 수준에서 관리가 불가능..
- 더불어 에러를 처리하는 코드와, 컨트롤러 코드가 함께 관리된다는 점도 단점
- 이 다음으로 HandlerExceptionResolver과 여러 구현체가 등장해서, 전역적으로 예외를 관리 가능하도록 했다.
- ModelAndView 객체를 이용하여 본문을 만들어 응답하게 되는데 이는 낮은 수준의 HttpServletResponse와 상호 작용한다고 한다. (전통적인 MVC 방식..?)
- 이는 만약 body 응답을 다양하게 주고 싶은 경우(예를 들어 JSON, XML)에는 대처가 어렵기 때문에, 이에 대응하기 위해 ResponseEntityExceptionHandler이 사용된다.
- 또한 @ControllerAdvice 이 사용되면서 이제 기존 컨트롤러와 에러를 처리하는 부분이 분리되어 관리될 수 있게 되었다!

#### ResponseEntity [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- 헤더와 바디 내용을 포함하는 http 요청을 다루는 HttpEntity<T> 클래스의 확장
- ResponseEntity는 HttpStatusCode 정보를 포함한다. 그렇기 때문에 http 응답을 처리하는 데 좀 더 특화되어 있음
#### DispatcherServlet [🔗](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html)
