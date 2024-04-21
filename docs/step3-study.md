# step3
> 스프링 카페 - DB 연결
- 기능을 구현하며 공부했던 내용들을 정리합니다
---

## 부모 클래스 메서드의 반환타입이 Collection일 때, 하위 클래스에서 Override하면서 반환타입을 List로 변경해도 문제 없을까?
- 자식 클래스에서 부모 클래스 메서드를 오버라이드 할 때 반환 타입을 더 구체적인 타입으로 변경하는 것은 허용됨
- 자바의 다형성에 기반
- 서브 타입 규칙에 따라 자식 클래스의 반환 타입은 부모 클래스 반환 타입의 서브 타입이어야 한다

## JdbcTemplate
- JDBC를 간편하게 사용할 수 있도록 도와주는 유틸리티 클래스
- 이 클래스를 사용하면 JDBC의 복잡한 부분을 추상화하여 간결한 코드로 데이터베이스에 접근할 수 있다
- 하지만 동적 쿼리문을 해결하기 어렵다는 단점이 있다

### 기존의 JDBC 사용
- Class.forName() 메서드로 Jdbc 드라이버를 메모리에 동적으로 로딩한다
- JDBC 드라이버는 DB 종류마다 다름
- 그 다음에는 DBMS 서버와 접속해야 한다. DriverManager 클래스의 getConnection() 메서드를 이용
- 연결에 성공하면 DB와 연결된 상태를 Connection 객체로 표현하여 반환한다
- getConncetion은 url, 로그인할 계정, 비밀번호를 인자로 받는다
- Statement 객체로 DB 쪽으로 SQL 문을 전송하고, DB는 처리된 결과를 다시 자바 프로그램으로 전달한다.
- createStatement() 메서드를 사용한다
- Statement에서 제공하는 executeQuery(String sql), executeUpdate(String sql) 메서드로 SQL을 전송하고 처리 결과를 받아온다
- executeQuery의 반환값인 ResultSet에 대해 해당 클래스가 가지고 있는 데이터 타입에 따라 추출하는 getter 메서드로 데이터를 가져올 수 있다
- 작업을 완료했다면 DB 관련 처리를 하며 사용한 모든 객체들을 메모리에서 해제한다
- 최근에 사용했던 순서대로 해제.
- try-with-resources문이나 try-catch 문을 사용해 이 모든 과정을 진행해야 한다

### JdbcTemplate 사용
- 자원을 관리하고 중복 코드를 줄이는 데 탁월
- 

## DELETE vs TRUNCATE vs DROP
- 

## 참고 링크
- https://giron.tistory.com/142
- https://codingwell.tistory.com/23