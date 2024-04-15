package codesquad.springcafe.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class UserH2Database implements UserDatabase {

    JdbcTemplate jdbcTemplate;

    public UserH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (userid, email, nickname, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getEmail(), user.getNickname(),
            user.getPassword());
    }

    @Override
    public void update(User user, String userId) {
        String sql = "UPDATE users SET email = ?, nickname = ?, password = ? WHERE userid = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getNickname(), user.getPassword(), userId);
    }

    @Override
    public User findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE userid = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
    }

    @Override
    public List<UserRequestDto> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        List<UserRequestDto> userRequestDtos = new ArrayList<>();
        for (User user : users) {
            userRequestDtos.add(
                new UserRequestDto(user.getUserId(), user.getEmail(), user.getNickname()));
        }
        return userRequestDtos;
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM users");
    }
}
