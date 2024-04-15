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

    private static final String INSERT_USER_SQL = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String DELETE_ALL_USERS_SQL = "DELETE FROM users";
    private static final String UPDATE_USER_SQL = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_SQL, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL, userRowMapper());
    }

    @Override
    public User findUserById(String userId) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL, userRowMapper(), userId);
    }

    @Override
    public void clear() {
        jdbcTemplate.update(DELETE_ALL_USERS_SQL);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_USER_SQL, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
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
