# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-4 로그인 구현

### ⚒ 기능 구현 목록

- [x] 로그인 기능
- [x] 로그아웃 기능
- [x] 로그아웃 상태에서 로그인 권한이 필요한 페이지에 접근하는 경우 차단
- [x] 프로필 수정 시 본인의 프로필만 수정 가능하도록 제한하는 기능

### ✍️ 메소드 convention

| url                              | 기능                              |
|----------------------------------|---------------------------------|
| GET /user/register               | 회원가입 화면 제공                      |
| POST /user/register              | 새로운 유저 회원가입 진행                  |
| GET /user/list                   | 전체 회원 리스트 화면 제공                 |
| GET /user/profile/:userId        | userId에 해당되는 유저의 프로필 화면 제공      |
| GET /qna                         | 질문을 작성할 수 있는 작성 폼 화면 제공         |
| POST /qna                        | 새로운 질문 등록                       |
| GET /article/:articleId          | articleId에 해당되는 질문의 상세 내용 화면 제공 |
| GET /user/profile/:userId/update | 회원정보 수정 폼 화면 제공                 |
| PUT /user/profile/:userId/update | 회원정보 수정 요청                      |
| GET /login                       | 로그인 화면 제공                       |
| POST /login                      | 로그인 진행                          |
| POST /logout                     | 로그아웃 진행                         |
| GET /                            | 홈 화면 - 전체 글 목록 표기               |

### 🤔 설계 및 고민

#### - modelAndView는 Redirection 시에 URL에 쿼리 파라미터가 표시된다 🥲

- 처음에는 로그인 시 동적으로 버튼을 다르게 표시하기 위해 컨트롤러에서 다른 요청들을 처리한 후에 마지막에 로그인 여부를 포함시키려는 생각으로 Interceptor의 posthandler를 사용했다.
- MedelAndView에 object를 추가하는 방식으로 로그인 여부를 판단하게 했는데, 이렇게 하는 경우 리다이렉트 후에 url에 쿼리 파라미터로 해당 값이 붙어서 출력된다는 문제가 생겼다.
  &rarr; `http://localhost:8080/?isLogin=true`
- ModelAndView를 전달한 후 리다이렉트가 되면, 새로운 요청이 일어나서 해당 내용은 초기화가 되기 때문에 그 내용을 유지하기 위해서 이렇게 값이 붙는다고 한다.
  하지만 지정된 요소들이 이렇게 직접 노출되는 것은 좋지 않다고 생각했고, 해당 값이 보이지 않게 하고 싶었다.
- 그러다 굳이 이렇게 따로 값을 주지 않아도 session 값을 바로 가져와서 thymeleaf에서 사용할 수 있다는 것을 알게 되었다!
- `session.setAttribute()`로 세션 값 세팅 &rarr; `th:if="${session.sessionUser}"`처럼 thymeleaf에서 사용

&rarr; **그런데 궁금한 점은 ModelAndView에 addObject로 인터셉터에서 추가해주는 경우에만 URI에 쿼리 파라미터가 보인다는 사실이다. (리다이렉트 시)**  
동일한 상황에서 일반 컨트롤러에서 ModelAndView를 이용해 추가를 할 경우에는 쿼리 파라미터가 표시되지 않는다 🤔

- 인터셉터에서 Model로 전달한 값이 리다이렉트 되는 경우에는 사라지기 때문에 쿼리 파라미터에 표시되어 전달된다고 하는데, 이는 인터셉터에서만 그렇게 작동되고 있다.
- 동일하게 일반 컨트롤러에서 model에 추가한 값이 url로 전달되도록 하기 위해서는 RedirectView에 RedirectAttributes로 추가를 해야 한다고 하는데, 왜 인터셉터는 model에만 추가해도
  쿼리로 보이는 것인지?

```
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView != null) {
        // 해당 값이 URI로 보인다
            modelAndView.addObject("isLogin",true);
        }
    }
    
        @PostMapping("/qna")
    public String register(RegisterArticle articleForm, ModelAndView model) {
        articleService.articleForm(articleForm);
        // 여기서 추가하는 경우에는 보이지 않는다.
        model.addObject("test",10);
        return "redirect:/";
    }
```

- 검색해서 정보를 찾기 어려워서 디버깅으로 흐름을 뜯어보았다.
- 그냥 컨트롤러에서 `model.addObject` 하면 일단 defaultModel에 추가가 되는데, 이후 리다이렉트 하는 과정에서 모델이 redirectModel로 변경된다.
    - `ModelAndViewContainer`의 `getModel`에서 redirectModel`을 사용할지 defaultModel을 사용할지 판단해서 모델을 세팅한다.
    - 판단 기준 중 `redirectModelScenario` 변수가 있는데, 뷰 이름에 redirect가 들어가기 때문에 이 부분을 체크해서 모델을 redirectModel로 사용하도록 하는 것
    - 그리고 이후 targetUrl을 만들 때, 모델에 있는 값을 확인해서 뒤에 쿼리로 붙이는데, 모델 안에 값이 없으므로 아무것도 붙지 않은 url이 만들어진다.
- 그런데 인터셉터에서 `model.addObject` 하면 해당 값이 모든 처리가 끝난 후 넘어오는 ModelAndView로 추가가 된다.
    - 이미 컨트롤러에서 redirectModel로 변경해서 인터셉터에 전달했고, 거기에 값을 추가하는 거니까 따로 위와 같이 리다이렉트인지 여부를 확인, 변경하는 작업은 일어나지 않고
      그래서 이번에는 ModelAndView에 추가된 값 그대로 targetUrl이 만들어진다.

#### - html 파일에서 로그아웃을 Post 요청 보내도록 수정 시, css가 적용되지 않는 문제

- 로그아웃 버튼을 누르는 경우 post 요청이 가도록 html을 수정했는데, a 태그로 구현되어 있던 부분을 form으로 변경하니 css가 기존과 다르게 잘 적용이 되지 않았다.
- 그러다 진짜 데이터를 전달하는 역할을 하는 form은 숨기고 보이는 부분은 기존 버튼 그대로 설정할 수 있는 방법이 있다는 것을 알게 되어 다음과 같이 구현했다.

```
                    <form th:action="@{/logout}" method="post" id="logout" style="display: none"></form>
                    <li th:if="${session.sessionUser}"><a href="#" role="button"
                                              onclick="document.getElementById('logout').submit()">로그아웃</a></li> 
```
#### RowMapper 생성하는 또 다른 방법
- `BeanPropertyRowMapper.newInstance`를 이용해서 특정 클래스의 RowMapper를 한 줄로 가져올 수 있다.
- 다만 해당 클래스에 자동으로 매핑해주기 위해서 빈 생성자와 모든 setter가 필요하다.

### 📚 학습 중

#### HttpSession [🔗](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/servlet/http/HttpSession.html)
> Provides a way to identify a user across more than one page request or visit to a Web site and to store information about that user.

- 세션의 역할을 하는 인터페이스
- `setAttribute`으로 객체를 세션에 바인딩할수 있고, `getAttribute`로 꺼내어 쓸 수 있다.
- 로그아웃을 하기 위해 사용한 `invalidate`은 세션을 무효화하고, 바인딩 된 객체들을 모두 삭제한다.
- 그밖에도 세션과 관련된 요청을 마지막으로 받은 시간을 알려주는 `getLastAccessedTime`, 세션 유효시간을 설정할 수 있는 `setMaxInactiveInterval`등 관련 기능들을 제공한다
#### WebMvcConfigurer [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html)
- spring mvc 설정을 커스터마이징 할 수 있는 기능을 제공하는 인터페이스
- 인터셉터뿐 아니라 리소스 핸들러나 뷰 리졸버 등 원하는 대로 설정을 변경할 수 있음
- 기존 bean 설정을 그대로 유지하고 원하는 부분만 바꾸고 싶다면 @EnableWebMvc 을 사용하지 않고 WebMvcConfigurer를 구현하면 Ok
#### HandlerInterceptor [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html)
- 핸들러(컨트롤러)의 실행 전/후에 특정한 로직을 수행할 수 있도록 하는 기능을 제공
- 3가지 메서드가 정의되어 있는데 호출 타이밍에 따른 차이가 있다.
  - `preHandle` : 핸들러가 호출되기 전에 해당 메서드가 호출된다.
  - `postHandle` : 핸들러가 호출되어 성공적으로 실행되고 나서, 뷰를 랜더링하기 전에 해당 메서드가 호출된다.
  - `afterCompletion` : 뷰가 랜더링이 되고 난 이후에 호출된다.
