package codesquad.springcafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class UserH2Database implements UserDatabase {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User(rs.getString("userid"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email"));
        return user;
    };

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO MAIN.USERS (userid, password, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT * FROM MAIN.USERS";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User getUser(String userId) {
        String sql = "SELECT * FROM MAIN.USERS WHERE userid = ?";
        List<User> userList = jdbcTemplate.query(sql, userRowMapper, userId);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE MAIN.USERS SET password = ?, name = ?, email = ? WHERE userid = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    @Override
    public boolean isExistUser(String userId) {
        return getUser(userId) != null;
    }
}
