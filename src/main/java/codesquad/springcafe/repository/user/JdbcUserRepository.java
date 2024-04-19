package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = userDto.toEntity();
        String SQL = "INSERT INTO users (user_id, nickname, email, password, created) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, user.getUserId(), user.getNickname(), user.getEmail(), user.getPassword(),
                user.getCreated());
    }

    @Override
    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        String SQL = "UPDATE users SET password = ?, nickname = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(SQL, userUpdateDto.getNewPassword(), userUpdateDto.getNewNickname(),
                userUpdateDto.getNewEmail(),
                userId);
    }

    @Override
    public List<User> findAllUsers() {
        String SQL = "SELECT * FROM users";
        return jdbcTemplate.query(SQL, rowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String SQL = "SELECT * FROM users WHERE user_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL, rowMapper(), userId));
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("user_id");
            String nickname = rs.getString("nickname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
            return new User(userId, nickname, email, password, created);
        };
    }
}