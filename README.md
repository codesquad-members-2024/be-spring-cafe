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

## step 3️⃣

### [공부한 내용]()
### 마주했던 문제 🤔

### h2 DB에 schema.sql로 ddl 자동 실행하기
- 문제 상황:
  - schema.sql을 추가했으나 h2 db에 적용되지 않았다
- 해결 방법:
  - application.properties 파일에 schema.sql을 찾는 경로 정보를 추가해서 해결!

### schema.sql의 첫 번째 구문을 인식하지 못함
- 문제 상황:
  - 코드 맨 앞줄에 주석을 추가했더니 users 테이블 drop이 되지 않았다
- 해결 방법:
  - sql의 주석 작성 방식을 착각해서 발생한 문제였다
  - sql의 주석은 '//'가 아니라 '--'이다!!

### 사용자 정보 수정 로직이 적용되지 않음
- 문제 상황:
  - 사용자 정보 수정을 시도했으나 전혀 변경되지 않고 이전과 같은 정보를 가지고 있다
- 해결 방법:
  - Local 버전에서는 HashMap으로 관리하므로 User 객체를 얻어 정보만 수정하면 수정 로직이 적용되었으나, JdbcTemplate을 사용하는 H2 버전의 경우 User 객체를 얻어 수정해도 해당 내용이 DB에 적용되지 않아 발생
  - 따라서 UserRepository에 update라는 메서드를 추가해 구현하도록 함으로써 해결
  - 해당 메서드는 H2버전에서 db에 접근해 update query를 실행함

### SQL 매핑 실패
- 문제 상황:
  - Question이라는 Entity 객체는 User라는 필드를 갖는데, 이는 DB 테이블 상에서 외래 키에 해당하며 userId라는 이름으로 저장되고 있다
  - 하지만 reflection을 활용해 구현한 SimpleRowMapper에서는 userId를 User 객체로 매핑하는 기능이 구현되어 있지 않다
- 해결 방법:
  - 처음에는 User 테이블과 조인하는 방법, Question 인스턴스인지 확인하고 userRepository의 findById를 이용해 user 정보를 조회한 뒤 필드를 세팅해주는 방법 등을 생각했다
  - 하지만 SimpleRowMapper를 만든 이유가 특정 클래스에 의존적이지 않게 만들기 위함이었는데, 이렇게 되면 의존적이 되고, 만약 지금보다 서로 연관관계가 많아진다면 얼마나 더 로직을 수정해야 할 지 모르는 상황이다
  - 따라서 가장 쉬운 방법으로 Question 객체가 User 타입 필드가 아닌, Long 타입 userId 필드를 갖도록 해 해결했다
  - 대신 QuestionService에서 사용자 정보와 함께 게시글 정보를 조회해 반환하는 로직에서, userRepository에 접근해 Question 객체에 저장된 userId 필드로 사용자를 한 번 더 조회해 주어야 한다.

### 조회수 업데이트
- 문제 상황 :
  - 게시글 상세조회 시마다 조회수가 업데이트 되어야 하지만, 다시 접속해도 조회수가 1이고 목록을 보면 모두 조회수가 0인 문제
- 해결 방법:
  - DB에 viewCnt 변경을 적용하지 않아서 발생한 문제였다
  - 별도의 viewCntUp이라는 메서드에서 업데이트 쿼리를 날리는 로직을 구성해 해결했다
  - 다만 하나의 로직에 쿼리가 두 번 날라가는 것이므로 뭔가 더 나은 해결책이 있는지 찾아봐야 할 필요가 있어 보인다

### 회원 탈퇴
- 고민 :
  - 회원탈퇴 시 민감한 데이터는 어떻게 관리해야 할까?
  - 이메일을 null로 처리하는 방식을 먼저 채택했으나 이 경우 회원이 계정 살리기를 요청하는 경우 이메일을 다시 받아야 함
- 해결 :
  - 그냥 deleted만 true로 update 하자
  - 내가 만든 서비스는 실 서비스 하지 않는데다가, 이메일 인증 로직도 없어 실제 존재하는 이메일인지 확실하지도 않아 괜찮다

### 엔티티 조회 시 외래 키로 엮인 다른 엔티티에 대한 조회 쿼리도 함께 날아가는 문제 (N+1 문제)
- 문제:
  - 게시글과 같이 다른 클래스의 PK를 외래키로 가지고 있는 클래스의 경우 게시글 작성자 조회를 위한 쿼리가 또 한 번 날아간다
  - 또한 게시글 전체 조회의 경우 게시글의 작성자 아이디를 표시하기 위해 작성자를 또 조회하는데, 게시글이 다섯개라면 사용자 조회 쿼리가 다섯 번 더 나간다.
  - N+1문제 발생
- 해결:
  - join을 사용해서 쿼리문을 변경하자!
  - Question과 User를 조인해서 조회하기 위해서는 Question 클래스 내 필드로 User가 있는 것이 좋다
  - 하지만 이 경우 SimpleRowMapper를 변경해야 했다
    - 매핑된 테이블 타입의 객체의 경우 직접 선언한 @AssociatedClass 어노테이션을 작성해준다
    - SimpleRowMapper 내부 로직에서 이름으로 필드 조회했을 때, 해당 어노테이션이 있는 경우 재귀적으로 다시 해당 객체에 대한 매핑 로직을 돌린다
  - 매핑에 성공했지만... join된 유저 아이디나 deleted가 잘못된 값으로 매핑되고 있다... 이게 무슨 일?

### 테이블 칼럼과 엔티티 필드 매핑
- 문제:
  - 위에서 작성한 문제는 해결했지만, 다른 문제가 발생했다!
  - Question에도 ID라는 칼럼이 있고, User에도 ID라는 칼럼이 있어 매핑 시 잘못된 값이 할당되고 있다
- 해결:
  - 

### 댓글을 조회할 때 게시글도 같이 조회
- 고민:
  - 댓글과 사용자, 질문 게시글도 연관관계가 있는 클래스인데, 댓글의 필드로 Question을 갖게 하니 댓글을 조회할 때 Question도 함께 조회된다
  - 만약 Question에 List<Comment>와 같은 타입의 필드가 있었다면 무한으로 순환하며 서로를 조회할 수도 있다.
- 해결:
  - Comment 클래스는 Long questionId 필드만 가지고, Question 필드는 가지지 않도록 지정했다.