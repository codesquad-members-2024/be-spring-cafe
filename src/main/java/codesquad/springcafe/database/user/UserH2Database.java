package codesquad.springcafe.database.user;

import codesquad.springcafe.model.User;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class UserH2Database implements UserDatabase {
    private final JdbcTemplate jdbcTemplate;

    public UserH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User add(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("users").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("nickname", user.getNickname());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("joinDate", user.getJoinDate());

        Number key = insert.executeAndReturnKey(params);
        user.setId(key.longValue());

        return user;
    }

    @Override
    public Optional<User> findBy(String nickname) {
        String sql = "select id, nickname, email, password, joindate from users where nickname = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), nickname);

        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, nickname, email, password, joindate from users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET nickname = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, user.getNickname(), user.getPassword(), user.getId());
    }

    @Override
    public void clear() {
        String sql = "delete from users";
        jdbcTemplate.update(sql);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            String email = rs.getString("email");
            String nickname = rs.getString("nickname");
            String password = rs.getString("password");

            User user = new User(email, nickname, password);
            user.setId(rs.getLong("id"));
            Date joindate = rs.getDate("joindate");

            user.setJoinDate(joindate.toLocalDate());

            return user;
        };
    }

}
