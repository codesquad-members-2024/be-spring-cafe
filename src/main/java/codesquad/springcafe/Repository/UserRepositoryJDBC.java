package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJDBC implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public UserRepositoryJDBC(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User create(User user) {
        // SimpleJdbcInsert 객체를 생성하고 JdbcTemplate을 사용하여 데이터베이스에 삽입할 테이블을 지정합니다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // 삽입할 테이블을 지정합니다.
        jdbcInsert.withTableName("users");
        // 삽입할 데이터를 맵 형태로 변환하여 설정합니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("userId", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("signUpDate", user.getSignUpDate());

        // 데이터를 삽입합니다.
        jdbcInsert.execute(parameters);

        return user;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET email=? WHERE userid=?";
        jdbcTemplate.update(sql, user.getEmail(), user.getUserId());
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select * from users where userid = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from users where email = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), email);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getString("userid"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setSignUpDate(rs.getDate("signUpDate").toLocalDate());
            return user;
        };
    }
}
