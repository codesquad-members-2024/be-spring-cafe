package codesquad.springcafe.domain.user.model;

import codesquad.springcafe.global.rowMapper.SimpleRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class UserRepositoryH2 implements UserRepository{
    private final Logger logger = LoggerFactory.getLogger(UserRepositoryH2.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleRowMapper<User> userRowMapper;

    @Autowired
    public UserRepositoryH2(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRowMapper = new SimpleRowMapper<>(User.class);
    }

    @Override
    public User save(User user) {
        final String sql = "insert into users(loginId, email, name, password) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLoginId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        Long key = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        user.setId(key);

        logger.info("User saved successfully! | userId: {} | query : {}", key, sql);

        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        final String sql = "select * from users where id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);

        if (users.size() > 1) {
            throw new RuntimeException("사용자를 두 명 이상 조회하고 있습니다.");
        }

        logger.info("User find by Id | userId: {} | query : {}", userId, sql);

        return users.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        final String sql = "select * from users";
        logger.info("Find All Users | query : {}", sql);
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        final String sql = "select * from users where loginId = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, loginId);

        if (users.size() > 1) {
            throw new RuntimeException("사용자를 두 명 이상 조회하고 있습니다.");
        }

        logger.info("User find by loginId | userLoginId: {} | query : {}", loginId, sql);

        return users.stream().findFirst();
    }

    @Override
    public Boolean existsById(Long userId) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";
        logger.info("User exists by Id | userId: {} | query : {}", userId, sql);
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }

    @Override
    public void update(Long userId, User updateUser) {
        final String sql ="UPDATE users SET name = ?, email = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, updateUser.getName());
            ps.setString(2, updateUser.getEmail());
            ps.setLong(3, userId);
            return ps;
        });

        logger.info("User update | userId: {} | query : {}", userId, sql);
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM users";
        logger.info("Delete All Users | query : {}", sql);
        jdbcTemplate.update(sql);
    }
}