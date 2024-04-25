package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.error.exception.UserNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public void createUser(User user) {
        String SQL = "INSERT INTO users (user_id, nickname, email, password, createdDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, user.getUserId(), user.getNickname(), user.getEmail(), user.getPassword(),
                user.getCreatedDate());
    }

    @Override
    public List<User> findAllUsers() {
        String SQL = "SELECT * FROM users";
        return jdbcTemplate.query(SQL, rowMapper());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String SQL = "SELECT * FROM users WHERE user_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL, rowMapper(), userId));
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(userId + "의 사용자가 존재하지 않습니다.");
        }
    }

    @Override
    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        String SQL = "UPDATE users SET password = ?, nickname = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(SQL, userUpdateDto.getPassword(), userUpdateDto.getNickname(),
                userUpdateDto.getEmail(),
                userId);
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("user_id");
            String nickname = rs.getString("nickname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            LocalDateTime createdDate = rs.getTimestamp("createdDate").toLocalDateTime();
            return new User(userId, nickname, email, password, createdDate);
        };
    }
}