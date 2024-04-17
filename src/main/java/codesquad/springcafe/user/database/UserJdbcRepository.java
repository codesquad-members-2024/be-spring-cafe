package codesquad.springcafe.user.database;

import codesquad.springcafe.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository {
    JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql,userRowMapper());
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,user.getUserId(),user.getPassword(),user.getName(),user.getEmail());
    }

    @Override
    public User findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE USER_ID = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper(), userId);
    }

    @Override
    public void updateUser(User user, String userId) {
        String sql = "UPDATE users SET USER_EMAIL = ?, USER_NAME = ?, USER_PASSWORD = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPassword(), userId);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("user_password"),
                rs.getString("user_name"),
                rs.getString("user_email")
        );
    }
}
