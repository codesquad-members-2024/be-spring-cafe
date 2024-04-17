package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
@Primary
@Repository
public class H2UserRepository implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> {
        User user = new User(
                resultSet.getString("userId"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return user;
    };

    public H2UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update("INSERT INTO USERS (userId, name, email, password) VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getName(), user.getEmail(), user.getPassword());
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
    }

    @Override
    public User getById(String userId) {
        final String SELECT_USER = "SELECT * FROM USERS WHERE userId= ?";
        return jdbcTemplate.queryForObject(SELECT_USER, userRowMapper, userId);
    }

    @Override
    public void update(User user) {
        final String UPDATE_USER = "UPDATE Users SET password=?, name=?, email=? WHERE userid=?";
        jdbcTemplate.update(UPDATE_USER, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
}
