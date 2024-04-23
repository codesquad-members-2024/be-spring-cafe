package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class H2UserRepository implements UserRepository {
    private final Logger logger = LoggerFactory.getLogger(H2ArticleRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    public H2UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update("INSERT INTO USERS (userId, name, email, password) VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getName(), user.getEmail(), user.getPassword());
        logger.debug("{} 유저 회원가입 완료", user);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
    }

    @Override
    public Optional<User> getById(String userId) {
        final String SELECT_USER = "SELECT * FROM USERS WHERE userId = ?";
        try {
            User user = jdbcTemplate.queryForObject(SELECT_USER, userRowMapper, userId);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        final String UPDATE_USER = "UPDATE Users SET password=?, name=?, email=? WHERE userid=?";
        jdbcTemplate.update(UPDATE_USER, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
        logger.debug("{} 유저정보 수정 완료", user);
    }
}