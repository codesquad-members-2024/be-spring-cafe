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
14. Spring JDBC 를 사용하여 리팩토링
- 