package codesquad.springcafe.user.respository;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.respository.util.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class H2UserRepository implements UserRepository{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void storeUser(User user) {
        jdbcTemplate.update("INSERT INTO Users (userId, password, email, name, createTime) values (?, ?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getEmail(), user.getName(), user.getCrateTime());

        logger.debug("id={} 인 유저 db에 저장 완료", user.getUserId());
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from USERS", new UserRowMapper());
    }

    @Override
    public User findByName(String name) throws NoSuchUserException {
        String sql = "select * from USERS where name= ?";

        List<User> result = jdbcTemplate.query(sql, new UserRowMapper(), name);
        if (result.isEmpty()) throw new NoSuchUserException();

        logger.debug("name={} 에 해당하는 유저 검색 완료", name);

        return result.getFirst();
    }

    @Override
    public User findById(String id) throws NoSuchUserException {
        String sql = "select * from USERS where userId= ?";

        List<User> result = jdbcTemplate.query(sql, new UserRowMapper(), id);
        if (result.isEmpty()) throw new NoSuchUserException();

        logger.debug("id={} 에 해당하는 유저 검색 완료", id);
        return result.getFirst();
    }

    @Override
    public void removeUser(String name) {
        String sql = "delete from USERS where name = ?";

        int update = jdbcTemplate.update(sql, name);

        logger.debug(String.format("%d행의 %s이름의 유저 삭제 완료", update, name));
    }

    @Override
    public boolean exist(String value) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE userId = ? OR name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, value, value);

        logger.debug(String.format("name 또는 userId 값이 %s인 행 %d 개 존재함", value, count));
        return count > 0;
    }
}
