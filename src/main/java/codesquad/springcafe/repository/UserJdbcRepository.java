package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    @Override
    public User findUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper(), userId);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM users";
        jdbcTemplate.update(sql);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("user_id");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new User(userId, password, name, email);
        };
    }
}
