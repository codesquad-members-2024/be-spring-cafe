# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페
##### eddy

과정
1. implementation 'org.springframework.boot:spring-boot-starter-web' 추가
2. build 설정 변경
3. ``implementation group: 'org.springframework.boot', name: 'spring-boot-starter-mustache', version: '3.2.4'`` 추가
4. 패키지 구조 생성
   1. Domain
   2. Controller
   3. Service
   4. Repository
5. 스프링 빈 등록
    1. 컴포넌트 스캔 방식
```
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
```
<li><a class="black-component" href="form" role="button">회원가입</a></li> 
```
- 절대경로 : href = "/~"
```
<li><a class="black-component" href="/form" role="button">회원가입</a></li>
```
8. javascript를 사용한 a태그 href 변경
9. 