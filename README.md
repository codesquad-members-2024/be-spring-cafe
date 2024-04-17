# be-spring-cafe

2024 마스터즈 백엔드 스프링 카페

## step-3 DB에 저장하기

### ⚒ 기능 구현 목록

- [x] H2 DB 연결
- [x] DB 테이블 생성
- [x] DB를 통해 게시글 저장, 조회하기
- [x] DB를 통해 사용자 정보 저장, 조회하기
- [x] EC2 인스턴스를 사용하여 배포하기

### 🐶 배포 주소 : [🔗](http://54.180.125.197:8080/) (0417~)

### 🤔 설계 및 고민

#### - DB 테이블 설계는 어떻게 할까?

- article은 pk의 기능을 할 수 있는 값이 필요하니까 auto increment를 이용해서 id를 따로 만듦
- user는 userId가 존재하고, 변경할 수 없는 값이기 때문에, pk의 역할을 충분히 할 수 있을 것 같아서 따로 id를 지정하지 않았다.

#### - article를 DB에 어떻게 저장하고 가져와야 하나?

- 기존에는 Article 타입을 다루는 임시 DB 역할을 하는 자료구조가 있었고, 거기에 저장을 하고 있었음
- 글을 저장할 때 기존에는 생성자에 `LocalDateTime.now`를 이용해서 시간을 세팅하도록 했다 &rarr;
  이때는 Article 객체로 바로 저장되고 또 Article 객체로 바로 꺼내서 사용했기 때문에 별 문제가 없었다.
- DB를 연결하게 되면서 DB의 내용과 자바 객체를 연결할 필요성이 생겨남. DB에 있는 값은 Article이 아님! 값들을 가져와서 객체로 만들어 주어야만 비로소 Article이 됨
- 그런데 가져온 정보로 Article을 만들면 time이 또 다시 현재 시간으로 세팅되는 상황이 발생
- 처음에는 기본 생성자와 setter를 사용해서 Article 내에서 다 처리하도록 했으나..  
  &rarr; request 요청으로 들어오는 정보는 제목, 글쓴이, 본문 3가지. 따라서 이 3개를 받아와서 저장을 위한 객체를 따로 만들자. 굳이 Article로 만든 다음에 저장할 필요가 없을 것 같다. (
  DTO)

```
// 게시글 db에 저장
    public void addArticle(RegisterArticle registerArticle) {
        jdbcTemplate.update("INSERT INTO Articles (writer, title, contents, time) VALUES (?,?,?,?)",
                registerArticle.getWriter(), registerArticle.getTitle(), registerArticle.getContents(), registerArticle.getTime());
    }
}
```

#### - 로그를 찍고 싶다..! [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/simple/SimpleJdbcInsert.html)

- 저장하면서 로그로 저장되었다는 정보를 표시하고 싶은데, 위와 같이 구현할 경우 방금 저장된 Article을 다시 가져오기가 어려웠다.
- SimpleJdbcInsert라는 클래스를 알게 되어 해당 클래스를 사용하도록 코드를 수정해보았다.
- SimpleJdbcInsert
    - DB 테이블에 INSERT하는 간단한 기능을 제공하는 클래스
    - INSERT문의 작성에 필요한 정보 메타데이터를 처리해준다.
    - 테이블 이름, 컬럼 이름, 컬럼 값이 저장되어 있는 Map으로 사용할 수 있음
  ```
      public void add(Article article) {
        // SimpleJdbcInsert를 생성하고, insert할 테이블명과 자동으로 키가 생성되는 컬럼의 이름을 지정
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Articles")
                .usingGeneratedKeyColumns("id");
        
        // map에 insert할 값들을 세팅
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("time", article.getTime());

        // executeAndReturnKey 메서드를 통해, 실행 후 아까 지정한 id 컬럼을 가져온다
        Long key = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        article.setId(key);
    }
  ```
- 이제 저장된 Article이 무엇인지 알 수 있게 되었다.
- 추가로 Map 자료구조 대신 SqlParameterSource 인터페이스 구현체를 이용해서 값을 넘겨줄 수도 있다.
    - MapSqlParameterSource &rarr; addValue 메서드 체이닝으로 Map의 put처럼 값을 추가한다.
    - BeanPropertySqlParameterSource &rarr; bean 객체로부터 파라미터 값을 바로 가져온다.

#### - 기존 DB 역할 클래스와 H2를 바꿔서 연결해도 정상적으로 바로 동작하도록 하고 싶다.

- Repository 어노테이션이 여러 개 존재하는 상황에서 Primary 어노테이션을 이용해서 원한다면 바로바로 DB를 갈아끼우고 싶다..
- 인터페이스를 활용해서 각 도메인에 해당하는 Repository를 만들었고, 또 각각 H2 데이터베이스와 기존 DB 클래스와 연결되는 Repository 구현체를 만들었다.
- Primary 어노테이션을 이용하면 spring이 해당 Repository를 찾아 데이터를 저장해 줄 것이다.

### 📚 학습 중

#### - thmyleaf

- 타임리프에서 객체의 프로퍼티에 접근할 때 메서드호출 방법, 프로퍼티 바로접근이 있는데 바로접근 방식 권장

#### - JdbcTemplate [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)

- jdbc는 자바와 데이터베이스를 연결할 수 있도록 하는 api
    - 이를 통해 어떤 종류의 DB와 연결을 하더라도 DB별로 api를 작성하지 않아도 되며, jdbc 하나의 표준을 이용해서 사용할 수 있게 되었다.
- 그리고 jdbcTemplate는 말 그대로 이런 jdbc를 잘 사용할 수 있도록 하는 템플릿 역할을 하는 클래스이다.

#### - queryForObject

- 단일 결과 행을 반환하는 sql 쿼리를 실행한 결과를 자바 객체로 매핑하는 메서드

```
// 전체 게시글의 수를 가져와서 Integer 객체로 매핑하는 메서드
public Integer getCountOfArticle() {
    String sql = "SELECT COUNT(*) FROM Articles";
    return jdbcTemplate.queryForObject(sql, Integer.class);
}
```

- 단일 컬럼일 경우에는 위와 같이 바로 매핑이 가능하지만, 컬럼이 여러개일 경우에는 RowMapper를 이용하여 매핑을 진행해야 한다.

```
    public User getUser(String id) {
        final String SELECT_USER = "SELECT * FROM USERS WHERE userId= ?";
        return jdbcTemplate.queryForObject(SELECT_USER, userRowMapper, id);
    }
```

#### - RowMapper [🔗](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html)

- DB의 레코드 값들을 자바 객체로 매핑하도록 하는 역할을 하는 인터페이스

```
    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
        User user = new User(
                resultSet.getString("userId"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return user;
    };
```

- resultSet을 이용해 컬럼의 값을 가져와서 User를 만드는 것을 확인할 수 있다.
- 람다를 이용해 RowMapper를 구현하여 사용해 보았다.