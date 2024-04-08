스프링 카페 - 학습 정리
===

# REST API
- REST API는 HTTP 요청을 통해 통신함으로써 리소스 내에서 레코드(CRUD 라고도 함)의 작성, 읽기, 업데이트 및 삭제 등의 표준 데이터베이스 기능을 수행
  - ```GET``` 요청 : 레코드 검색
  - ```POST``` 요청 : 레코드 작성
  - ```PUT``` 요청 : 레코드 업데이트
  - ```DELETE``` 요청 : 레코드 삭제
- 이러한 기능들 ```CRUD```라고 한다.
```java
@RestController
public class WebRestController {
    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld!";
    }
}
```
- 해당 요청은 ```GET```요청을 통해 레코드를 검색!

---