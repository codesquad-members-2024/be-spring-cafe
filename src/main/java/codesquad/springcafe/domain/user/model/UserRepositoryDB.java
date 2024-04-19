package codesquad.springcafe.domain.user.model;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class UserRepositoryDB implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryDB (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        String sql = "insert into users(email, name, password, created_at, modified_at) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(5, Timestamp.valueOf(user.getModifiedAt()));
            return ps;
        }, keyHolder);

        Long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        user.setId(key);
        return user;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return Optional.empty();
    }

    @Override
    public Boolean existsById(Long userId) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
