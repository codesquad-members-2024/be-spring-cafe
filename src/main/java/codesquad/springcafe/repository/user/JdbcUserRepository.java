package codesquad.springcafe.repository.user;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO `user` (userId, password, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM `user`";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            User user = new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")
            );
            return user;
        });
    }

    @Override
    public Optional<User> getById(String userId) {
        String sql = "SELECT * FROM `user` WHERE userId = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, rowNum) -> {
            User user = new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")
            );
            return user;
        });
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void modify(User modifiedUser) {
        String sql = "UPDATE `user` SET password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql,
            modifiedUser.getPassword(), modifiedUser.getName(), modifiedUser.getEmail(), modifiedUser.getUserId());
    }
}