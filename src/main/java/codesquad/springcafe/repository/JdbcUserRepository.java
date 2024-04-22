package codesquad.springcafe.repository;

import codesquad.springcafe.dto.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO user (userId, password, name, email) VALUES (?, ?, ?, ?)";
        return null;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
