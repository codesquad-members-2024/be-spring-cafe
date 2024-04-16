package springcafe.user.repository;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springcafe.user.model.User;

import java.util.List;


@Repository
public class H2UserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public H2UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public void insert(User user) {
        jdbcTemplate.update("INSERT into USERS (USERID, PASSWORD, NAME, EMAIL) values (?,?,?,?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

    }

    @Override
    public User findById(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT USERID, PASSWORD, NAME, EMAIL FROM USERS WHERE USERID =?",
                new Object[]{userId},
                (rs, rowNum) ->new User(
                rs.getString("USERID"),
                        rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getString("EMAIL"))
        );
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT USERID, PASSWORD, NAME, EMAIL FROM USERS",
                (rs, rowNum) ->new User(rs.getString("USERID"),rs.getString("PASSWORD"),
                        rs.getString("NAME"), rs.getString("EMAIL"))
        );
    }
}
