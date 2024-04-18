package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {
    private static final String INSERT_USER_SQL = "INSERT INTO users (user_id, password, name, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";
    private static final String DELETE_ALL_USERS_SQL = "DELETE FROM users";
    private static final String UPDATE_USER_SQL = "UPDATE users SET password = ?, name = ?, email = ? WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(UserDto userDto) {
        return null;
    }

    @Override
    public User updateUser(String userId, UserUpdateDto userUpdateDto) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return Optional.empty();
    }
}