package codesquad.springcafe.DB;

import codesquad.springcafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class JdbcUserDatabase {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // 스프링의 JDBC 추상화 계층
    }

    // RowMapper
    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> new User(
                rs.getString("user_id"),
                rs.getString("user_nickname"),
                rs.getString("user_email"),
                rs.getString("user_password"));
    }

    public User saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("user_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getUserId());
        parameters.put("user_password", user.getPassword());
        parameters.put("user_nickname", user.getNickname());
        parameters.put("user_email", user.getEmail());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));

        return user;
    }

    public List<User> getAllUser() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    public Optional<User> findByUserId(String userId) { // userId로 회원 찾기
        List<User> userList = jdbcTemplate.query("select * from users where user_id = ?", userRowMapper(), userId);
        return Optional.ofNullable(userList.get(0));
    }

    public void deleteUser(String userId) {
        jdbcTemplate.update("delete from users where user_id = ?", userId);
    }

}
