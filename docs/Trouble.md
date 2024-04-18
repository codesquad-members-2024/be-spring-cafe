## 정확히 10번 요청 후 db 연결이 끊기는 이슈 😡😡😡😡

### 문제 상황

DB와의 연결을 사용하는 메서드가 10회 실행된 후, DB 연결 오류가 발생
- 오류 발생 당시 코드
```java
    public List<UserListRes> findAll() {
            try (PreparedStatement query = dataSource.getConnection().prepareStatement(FIND_ALL_USER)) {
                try (ResultSet resultSet = query.executeQuery()) {
                    List<User> users = rowToUser(resultSet);
                    return getUserList(users);
                }
            } catch (SQLException e) {
                throw new RuntimeException(this.getClass() + ": findAll: " + e.getMessage());
            }
    }
```

### 해결 과정
##### DB 커넥션 확인 관련 설정 확인
아래 코드를 추가해도 해결되지 않음 
```yaml
    hikari:
      connection-timeout: 1000
      connection-test-query: SELECT 1
```

##### 프로젝트를 새로 만들어 다른 로직에 문제가 있는지 확인
동일한 문제 발생

##### SELECT * 쿼리에서 문제가 발생하는지 확인 위해 다양한 쿼리 실행
모두 동일한 문제 발생

##### 커넥션 풀 최대 개수 설정
**10회 이상 실행해도 문제가 발생하지 않음!!**
```yaml
      maximum-pool-size: 1000
```
[참고한 글](https://stackoverflow.com/questions/4437153/h2-database-embedded-connection-timeout)

문제는 해결됬지만, `풀 개수 == 최대 요청 수` 는 말이 안된다고 느낌

### 해결

`try with resources` 로 사용중이라고 생각했던 커넥션 풀이 반환되지 않고 있었음
**코드를 아래와 같이 수정하여 해결!!!**

```java
    @Override
    public List<Article> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL_SQL)) {
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToArticle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findAllArticle : " + e.getMessage());
        }
    }
```