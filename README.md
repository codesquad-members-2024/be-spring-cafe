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

