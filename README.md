# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페

## 🔒 규칙 설정
- 도메인 패키지 구조 적용
    - **도메인을 기준으로 패키지를 나눈 구조**
    - 예를 들어 회원에 관련된 기능은 user 패키지에 포함시킨다

- 브랜치 전략
    - 브랜치는 main - sharpie1330 - feature로 구성
    - 브랜치명은 `feature{feature-number}`

- 커밋 컨벤션
    ```
    * prefix *
    FEAT: 새로운 기능의 추가
    REMOVE: 파일, 코드 삭제 또는 기능 삭제
    FIX: 버그 수정
    DOCS: 문서 수정
    STYLE: 스타일 관련 수정 (코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
    REFACTOR: 코드 리팩토링
    TEST: 테스트 관련 코드
    CHORE: 빌드 관련 수정 (application.yml, build.gradle, .gitignore ..)
    COMMENT: 필요한 주석 추가 및 변경
    RENAME: 파일 혹은 폴더명 수정하거나 옮기는 작업만인 경우
    ```

## step1️⃣
### [공부한 내용](https://github.com/sharpie1330/be-spring-cafe/wiki/ModelAttribute,-WebMvcConfigurer,-Application-Context)
### 마주했던 문제 🤔

### mvc cannot resolve mvc view 오류

- 문제 상황
  - controller에서 users/register url에 회원가입 페이지를 매핑 시켜주려고 시도
  - 별도의 설정이 없다면, 템플릿 엔진은 src/main/resources/templates 를 기본으로 찾으므로 user/form을 반환하도록 구성
  - 이렇게 했더니 white label error page만 뜨고 원하는 화면이 나오지 않는 오류가 발생했다!
- 해결 방법
  - mustache 템플릿 엔진은 기본으로 suffix가 `.mustache`인 파일을 찾는다.
  - 따라서 html을 찾을 수 있도록 application.properties 파일에 `spring.mustache.suffix=.html`을 한 줄 추가해주어 해결했다.

### mustache 한글 깨짐 문제

- 문제 상황
  - 페이지는 잘 로딩이 되는데 한글이 깨지는지 ????만 출력된다.
  - 스프링부트 2.6.x 버전까지는 한글 인코딩 오류 없이 화면이 잘 출력된다. 하지만 최근 스프링 부트 버전이 3으로 업그레이드 되었다.
- 해결 방법
  - application.properties 파일에 `server.servlet.encoding.force=true`를 추가해주어 해결했다.
  - 해당 설정은 스프링부트 어플리케이션에서 서블릿 요청 및 응답의 문자 인코딩을 강제로 지정하는 것을 의미한다.
  - 기본적으로 서블릿 컨테이너는 요청과 응답의 문자 인코딩을 클라이언트와 서버 사이의 통신에서 사용되는 인코딩으로 설정하는데, 이렇게 강제로 지정하면 모든 서브릿 요청과 응답이 해당 인코딩을 사용하게 된다.

### Cannot resolve parameter names for constructor 오류
- 문제 상황
  - ModelAttribute 어노테이션이 붙은 대상 객체의 생성자 파라미터와 입력 데이터 바인딩이 되지 않아 발생한 문제
- 해결 방법
  - ConstructorProperties 어노테이션을 해당 객체 생성자에 작성해 해결
  - 입력 데이터명을 파라미터 순서에 맞게 적어주었다
- 문제 분석
  - 원인을 파악하고 싶어 디버깅과 구글링을 통해 분석해보았다
  - ModelAttributeMethodProcessor의 resolveArgument 메서드는 컨트롤러 메서드의 파라미터 오브젝트를 만들어 반환하는데, 이때 해당 타켓 타입의 생성자의 파라미터와 입력 데이터를 알맞게 바인딩해준다. (다른 방식도 있지만 여기서는 내가 사용한 방식만 설명한다)
  - 이때 내가 작성한 UserJoinData의 생성자의 파라미터들과 적절히 바인딩이 되어야 하는데, Executable의 getParameters0()라는 네이티브 메서드가 코드의 파라미터를 읽어오지 못해 파라미터 이름을 arg0, arg1, arg2와 같은 형식으로 읽어오고 있다
  - 결국 클라이언트가 회원가입 시 요청하는 email, name, password와 이름이 일치하지 않아 바인딩에 실패하는 것이다
  - 이 문제는 다른 사람의 컴퓨터로 실행했을 때 발생하지 않았던 오류로, 사용자 컴퓨터 환경에 따라 다르게 동작할 수 있는 것 같다.
  - 하지만 파라미터 이름을 가져오는 메서드에서 ConstructorProperties 어노테이션이 있는 경우를 우선적으로 처리하기 때문에 해당 어노테이션을 기술해주면 컴퓨터에 상관없이 잘 작동할 것이다
  - 따라서 이렇게 문제를 종결했다

### Port 8080 was already in use
- 문제 상황
  - 인텔리제이 응답없음 이슈로 작업 관리자로 강제 종료했더니, 가동 중이었던 SpringCafeApplication 프로세스가 죽지 않아 서버를 구동할 수 없는 상황
- 해결 방법
  - jps 명령으로 실행 중인 어플리케이션의 PID를 찾고, kill ${PID} 명령으로 해당 프로세스를 종료했다
  - 다만 유닉스 계역 운영 체제에서만 kill 명령이 실행 가능하기 때문에 윈도우 PowerShell로 실행해야 했다

### PathVariable name 작성 누락
- 문제 상황
  - 에러 메시지: Name for argument of type [java.lang.Long] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.
- 해결 방법
  - 스프링 부트 3.2부터 자바 컴파일러에 -parameter 옵션을 넣어주어야 어노테이션의 이름을 생략할 수 있다
  - name 속성을 알맞게 작성하여 해결
  - 관련 링크 : https://www.inflearn.com/questions/1087879/pathvariable-name-%EC%83%9D%EB%9E%B5-%EC%A7%88%EB%AC%B8-%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4

### Map의 put에 대한 잘못된 이해
- 문제 상황
  - user를 저장하고 반환되는 user 객체에서 getId()를 시도했으나, NPE 발생
- 코드
  ```
    public User save(User user){
        long userId = sequence.incrementAndGet();
        user.setId(userId);
        return users.put(userId, user);
    }
  ```
- 해결 방법
  - map의 put의 반환타입이 Map<K, V>의 V여서 당연히 지금 저장한 값의 결과를 반환할 것이라고 생각했다.
  - map의 put은 새로운 요소를 추가할 때 해당 요소의 이전 값이 있으면 그 값을 반환한다
  - 이 경우 새로운 요소가 추가되는 경우므로 이전 값이 없어 null을 반환하고, 무엇보다 그 이전 값이 있더라도 해당 값을 활용해서는 안 된다.
  - 저장하려는 user 객체를 반환하도록 수정하여 해결

## step 2️⃣

### [공부한 내용](https://github.com/sharpie1330/be-spring-cafe/wiki/%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%95%94%ED%98%B8%ED%99%94,-CSRF-Token,-Filter%EC%99%80-Interceptor)
### 마주했던 문제 🤔

### 인터셉터를 거칠 url 패턴 접근 시 css 적용 안되는 문제
- 문제 상황
  - 로그인 여부를 확인할 인터셉터를 만들어 config 클래스에 등록했는데, css가 적용되지 않은 html만 표시되었다
- 원인 분석 & 해결
  - 인터셉터가 CSS 파일에 대한 요청까지 가로채어 발생하는 문제.
  - /static/** 경로를 제외할 url 패턴으로 등록해 해결했다

### 캐시 삭제
- 적용하고 싶었던 것
  - 로그인이나 회원가입 완료를 하고 이전 화면으로 돌아갔을 때 내가 제출했던 내용이 그대로 남아있지 않기를 원했다
#### 시도 1: Cache-Control 헤더를 추가하는 Filter를 등록
  - 장점
    - 브라우저와 중간 캐시가 응답을 캐시하지 않아 항상 서버에서 새로운 데이터를 가져온다
    - 중복된 요청이나 오래된 캐시 데이터로 인한 문제를 방지
    - 사용자에게 항상 최신의 콘텐츠를 제공
  - 단점
    - 서버 부하가 증가하고 네트워크 트래픽이 증가한다
    - 매번 서버에 요청을 보내야 하고 대용량 파일이나 이미지를 사용하는 경우 트래픽 증가가 더 심각할 수 있다
  - 결론: 단점이 더 크므로 사용하지 말자
  - 또한 해당 방법은 내가 원하는 결과를 가져오지 못했다

#### 시도 2: javascript로 뒤로가기 이벤트가 발생하면 새로고침을 하자
  - 뒤로가기를 하든, 앞으로가기를 하든 해당 사이트가 새로 로드되어야 한다
  - Back/Forward Cache
    - BFCache는 페이지를 이동할 때 js를 포함한 전체 페이지를 캐시하고, 사용자가 뒤로 또는 앞으로 페이지를 이동할 떄 페이지의 전체 상태를 복원하는 브라우저 최적화다
    - 이전에 작성된 요청에 대한 응답만 포함하는 HTTP 캐시와 달리 전체 페이지의 스냅샷
    - 이런 bfcache로부터 복원된 데이터 중 사용자 상태를 유지해야 하는 민감한 정보가 있는 경우 데이터를 업데이트 해야
  - 해결 방법
    - 'pageshow' 이벤트 발생 시 back_forward 동작에 대해 감지하고 새로고침을 하는 로직을 js로 구현해 전역적으로 적용했다
    - 참고 자료 (자세한 것은 참고 자료 참조)
      - https://onbaba.tistory.com/4
      - https://kghworks.tistory.com/117
      - https://goldpumpkin.co.kr/entry/%EB%92%A4%EB%A1%9C%EA%B0%80%EA%B8%B0-%EC%B2%B4%ED%81%AC-%ED%9B%84-%EC%83%88%EB%A1%9C%EA%B3%A0%EC%B9%A8%ED%95%98%EA%B8%B0
  - **하지만, 여전히 문제가 있다...**
    - 가끔 회원가입 성공 페이지에 앞으로 가기, 뒤로 가기 버튼을 통해 접근하면, 기존의 요청이 다시 전송되어지는 문제로 '중복된 사용자' 예외가 발생한다!

#### 시도 3 (해결): 회원가입 요청 리다이렉트, 회원가입, 로그인 후 해당 페이지 접근 시 메인 페이지로 리다이렉트
- 다시, 어떤 문제?
  - 회원가입 요청에 대한 결과로 환영 페이지 뷰를 리턴하고 있다
  - 뒤로가기 후 앞으로가기를 하면 실제로는 같은 URL에서의 요청이므로 문제가 발생할 수 있다
  - 브라우저는 이전 페이지의 캐시된 버전을 보여주기 때문에 사용자는 다시 이전 페이지에 대해 같은 요청을 보내는 것처럼 보인다
- 해결 방법
  - /user 라는 포스트 요청에 대한 결과 페이지 url도 /user로 표시되는데, 아예 /welcome/{userId}와 같은 형식의 url로 redirect를 시키자
- 추가 기능
  - AfterAuthorizeInterceptor를 추가해서, 회원가입, 로그인 후 해당 페이지에 다시 접근 시 그냥 메인 페이지로 리다이렉트 시켜 주자
  - 이 기능은 굳이 필요 없을 수 있지만 편의상으로 기능을 추가했다. 일단 추가해 두고 추후 문제가 생기면 제거하기로 한다
