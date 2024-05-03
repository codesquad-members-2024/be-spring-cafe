# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페
##### eddy

배포 URL :<a href="3.35.21.0:8080"> 실행하기 </a>

과정
1. implementation 'org.springframework.boot:spring-boot-starter-web' 추가
2. build 설정 변경
3. build.gradle 추가
```groovy
implementation group: 'org.springframework.boot', name: 'spring-boot-starter-mustache', version: '3.2.4'
```
4. 패키지 구조 생성
   1. Domain
   2. Controller
   3. Service
   4. Repository
5. 스프링 빈 등록
    1. 컴포넌트 스캔 방식
```JAVA
@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // 스프링 생성할때 생성 -> 스프링 컨테이너의 서비스와 연결
    }
    ...
}
-> component 스캔에 의해 등록된 빈 주입
@Service //component scan
public class UserService {
  private final UserRepository userRepository;

   @Autowired
   public UserService(UserRepository userRepository){
   this.userRepository = userRepository; //구현체 주입
   }
   ...
}   

@Repository
public class UserRepositoryImpl implements UserRepository{
   ...
}
```
6. 로그 설정 application.properties에 적용
   - logging.level.root=info
   - logging.level.codesquad.springcafe=debug
7. a 태그 경로 학습
- 상대경로
```HTML
<li><a class="black-component" href="form" role="button">회원가입</a></li> 
```
- 절대경로 : href = "/~"
```HTML
<li><a class="black-component" href="/form" role="button">회원가입</a></li>
```
8. javascript를 사용한 a태그 href 변경
9. mustache 분리
10. HiddenHttpMethodFilter는 폼에서 전달된 _method 파라미터의 값을 읽어 실제 HTTP 메서드로 요청을 변경합니다.
```PROPERTIES
spring.mvc.hiddenmethod.filter.enabled=true
```
11. POST와 PUT의 차이
- POST : 멱등 X
  - POST는 리소스의 생성을 담당한다.
  - POST는 요청 시 마다, 새로운 리소스가 생성된다.
- PUT : 멱등 O
  - PUT은 리소스의 생성과 수정을 담당한다.
  - PUT은 요청 시 마다, 같은 리소스를 반환한다

12. JDBC 적용 후 H2데이터 베이스와 연동
- build.gradle 추가
```groovy
	implementation 'org.springframework.boot:spring-boot-starter-jdbc'
	implementation 'com.h2database:h2'
```
- application.properties추가
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:~/springcafe
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:sql/ddl.sql
```
- resources/sql/ddl.sql 작성 : users, posts 두개의 테이블 생성

13. PostRepositoryJDBC와 UserRepositoryJDBC 를 인터페이스를 상속받아 구현하고 @Repository 어노테이션을 통해 빈 등록
14. MUSTACHE IF문
```mustache
{{#isTrue}}
This will be shown if isTrue is true.
{{/isTrue}}
{{^isTrue}}
This will be shown if isTrue is false.
{{/isTrue}}
```
14. Spring JDBC template 를 사용하여 리팩토링
15. mustache에서 세션 사용 가능하도록 설정
```mustache
spring.mustache.servlet.expose-session-attributes=true
```
16.HttpSesion을 활용하여 로그인 구현
- http session 접속 
```groovy
#session time-out
server.servlet.session.timeout=1800
```

17. HttpSession 학습
- HttpSession을 가지고 session을 다룰 수 있게된다
- login(~,HttpSession httpSession)
- 매개변수로 HttpSession을 받는 시점에서 세션 생성
- setAttribute 시점에서 메모리에 세션 정보를 저장한다.
```java
//로그인 성공시 httpSession에 정보를 넣는다
httpSession.setAttribute("loginUserId", loginUserId);
```
```java
    public String login(HttpSession httpSession){
        System.out.println(httpSession.getClass().getName());
        //org.apache.catalina.session.StandardSessionFacade
    }
```
```java
//StandardSession
/**
 * The collection of user data attributes associated with this Session.
 */
protected ConcurrentMap<String,Object> attributes = new ConcurrentHashMap<>();
```
자료 :
1. https://tomcat.apache.org/tomcat-8.0-doc/api/org/apache/catalina/session/StandardSessionFacade.html
2. https://tomcat.apache.org/tomcat-8.0-doc/api/org/apache/catalina/session/StandardSession.html
3. https://github.com/apache/tomcat

18. Facade 패턴
- Facade 패턴은 복잡한 서브시스템의 일부 또는 전체를 단순화된 인터페이스로 래핑하는 구조를 갖는다. 이를 통해 클라이언트는 단일 인터페이스를 통해 복잡한 시스템을 사용할 수 있다.

19. 세션 생성 방식
```
import org.apache.catalina.util.StandardSessionIdGenerator; // 세션 ID 생성기
import org.apache.catalina.session.ManagerBase; //세션 매니저 구현체
```
//아래는 Manager 구현체인 ManagerBase의 시그니처
```
public abstract class ManagerBase extends LifecycleMBeanBase implements Manager {}
```
20. 인터셉터를 사용하여 인증
- 애플리케이션 여러 로직에서 공통으로 관심이 있는 있는 것을 공통 관심사(cross-cutting concern)
- 웹과 관련된 공통 관심사를 처리할 때는 HTTP의 헤더나 URL의 정보들 이 필요한데, 서블릿 필터나 스프링 인터셉터는 `HttpServletRequest` 를 제공한다.
- HTTP 요청 ->WAS-> 필터 -> 서블릿 -> 스프링 인터셉터 -> 컨트롤러 
- 스프링 인터셉터는 스프링 MVC가 제공하는 기능이기 때문에 결국 디스패처 서블릿 이후에 등장하게 된다. 스프 링 MV    C의 시작점이 디스패처 서블릿이라고 생각해보면 이해가 될 것.
- 스프링 인터셉터는 체인으로 구성                     
- preHandle, postHandle, afterCompletion 메서드로 구성됨
-  