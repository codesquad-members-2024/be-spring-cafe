package codesquad.springcafe.user.dao;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
public class UserH2Dao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserH2Dao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        final String sql = "INSERT INTO USERS (userId, password, nickname, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getNickName(), user.getEmail());
    }

    @Override
    public Collection<User> getAllUsers() {
        final String sql = "SELECT userId, nickname, email FROM USERS";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    @Override
    public Optional<User> findUser(String userId) {
        final String sql = "SELECT nickname, email FROM USERS WHERE userId = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper(), userId));
    }

    @Override
    public void updateUser(User user) {
        final String sql = "UPDATE USERS SET password=?, nickname=?, email=? WHERE userId=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getNickName(), user.getEmail(), user.getUserId());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("userId");
            String nickname = rs.getString("nickname");
            String email = rs.getString("email");
            return new User(userId, nickname, email);
        };
    }
}
