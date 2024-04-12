# be-spring-cafe
2024 마스터즈 백엔드 스프링 카페
## step-1 회원 가입 및 목록 조회 기능
### 기능 구현 목록
- [x] 회원가입 기능
- [x] 회원 리스트를 보여주는 기능
- [x] 특정 유저의 프로필을 확인하는 기능

### 메소드 convention
| url               | 기능                         |
|-------------------|----------------------------|
| GET /user         | 회원가입 화면 제공                 |
| Post /user        | 새로운 유저 회원가입 진행             |
| GET /users        | 전체 회원 리스트 화면 제공            |
| GET /users/userId | userId에 해당되는 유저의 프로필 화면 제공 |

### 🤔 설계 및 고민
#### - /user 요청과 /user/index.html 요청을 둘 다 동일한 곳에서 처리하고 싶다.
- getMapping 어노테이션을 사용할 때, value를 이용해 두 가지를 모두 처리하도록 수정하였다.
```
    @GetMapping(value = {"/user", "/user/form.html"})
    public String showForm() {
        return "user/form";
    }
```
### 📚 학습
#### - @Controller와 @RestController의 차이
- @Controller[🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html)
  - 해당 어노테이션이 달려 있는 클래스가 컨트롤러 역할을 한다는 의미
  - 스프링이 클래스패스를 스캔하면서 자바 빈으로 등록하는 클래스들에게 부여되는 어노테이션인 @Component의 특수한 모습인데, 그렇기 때문에 @Controller또한 스캔 되어서 해당 클래스가 컨트롤러임을 확인하게 된다.
- @RestController[🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)
  - @Controller와 @ResponseBody 두 개의 어노테이션을 하나로 사용한다고 생각하면 간단
  - 해당 어노테이션은 컨트롤러의 메서드가 실행한 결과를 뷰를 생성해서 해당 페이지로 응답하는 것이 아니라 JSON/XML 등으로 응답 body에 작성해서 클라이언트에게 보내게 된다.
#### - @SpringBootApplication의 의미 [🔗](https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-using-springbootapplication-annotation.html)
- 하나 이상의 빈을 선언하고, 자동 구성 [(auto-configuration)](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html)
& 구성 요소 검색 [(component scan)](https://docs.spring.io/spring-framework/docs/6.1.5/javadoc-api/org/springframework/context/annotation/ComponentScan.html)을 트리거하는 역할을 한다.
- 많은 어노테이션을 하나로 사용하게 하는 편의 어노테이션인데, 그중 주요 3가지 어노테이션은 다음과 같다.
  - @Configuration &rarr; 말 그대로 현재 클래스가 설정을 담당하는 클래스라는 의미이다. 해당 클래스는 빈을 등록할 수 있고, 또 다른 설정 파일을 참고하여 프로젝트를 구성할 수 있다.
  - @ComponentScan &rarr; 패키지를 스캔해가면서 해당되는 클래스들을 빈으로 등록해 주는 역할을 한다.
  - @EnableAutoConfiguration &rarr; 라이브러리나 설정 등등을 찾아서 구성하여 자동으로 애플리케이션을 실행하도록 도와준다.

#### - 타임리프란? [🔗](https://www.thymeleaf.org/documentation.html)
- 서버사이드 자바 템플릿 엔진으로, 자바와 템플릿을 결합하여 데이터를 뷰에 표시하는 기능을 제공
- 즉 HTML 템플릿에 자바 코드의 내용을 결합하여 동적으로 웹 페이지를 만들도록 도와준다.

#### - spring boot에서의 logger
- 먼저 현재 프로젝트에서는 [`slf4j🔗`](https://www.slf4j.org/) 인터페이스를 사용하고 있다.
- 이는 원하는 logger 구현체들을 자유롭게 사용할 수 있도록 하기 위한 것에 의미가 있다. (유연성)
```
import org.slf4j.Logger;

private Logger logger = LoggerFactory.getLogger(UserController.class);
```
- LoggerFactory에서 getLogger를 호출하면 SLF4JServiceProvider에서 로거를 반환해 주는데,
  spring boot에서는 기본적으로 logback을 사용하기 때문에 logback이 반환된다.
- logback 또한 slf4j을 구현한 프레임워크이기 때문에 사용이 가능한 것!
- logback-spring.xml 파일을 만들어서 원하는 대로 로그를 설정할 수 있다.

#### - logback-spring.xml로 로그 커스텀하기 [🔗](https://logback.qos.ch/documentation.html)
`<configuration>` &rarr; logback 파일의 시작점. 해당 태그 내부에 있는 요소들이 파일에 적용된다
`<conversionRule>` &rarr; 변환시키는 rule에 대한 부분. conversionWord을 만나면 converterClass이 적용되도록
`appender` &rarr; 로깅 이벤트를 작성하도록 하는 부분. 그래서 현재 코드에 보면 어떤 형식으로 로그를 작성할 것인지에 관한 pattern이 내부에 들어가 있다.  
`root` &rarr; root 로거에서의 변경은 기본적으로 프로젝트 전체에 영향을 준다. 하지만 따로 로거를 지정해서 해당 로거에만 적용되도록 설정을 조절할 수도 있다.

### 🎡 실수
#### - Whitelabel 오류
```java
@RestController
public class WebRestController {
    @GetMapping("/hello")
    public String Hello(){
        return "HelloWorld";
    }
}
```
- 문제상황: 위와 같이 get hello 요청이 들어올 경우에 HelloWorld 문자열을 화면에 출력하고자 했으나, 
계속 아래와 같은 Whitelabel 에러 페이지가 표시되는 중
```
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.

Mon Apr 08 11:08:48 KST 2024
There was an unexpected error (type=Not Found, status=404).
```
- 이유는 main 코드가 포함되어 있는 SpringCafeApplication과 컨트롤러인 WebRestController이 다른 패키지 안에 있었기 때문이었다.
- SpringCafeApplication이 위치한 패키지 내부로 컨트롤러들을 이동시켰더니 정상적으로 HelloWorld가 출력되었다.