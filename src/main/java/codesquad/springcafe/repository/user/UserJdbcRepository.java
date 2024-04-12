package codesquad.springcafe.repository.user;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import codesquad.springcafe.exception.db.UserNotFoundException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // Spring 프레임워크에서 제공하는 JDBC 추상화 계층
    }

    @Override
    public User addUser(User user) {
        // 단순한 삽입 작업이므로 SimpleJdbcInsert 사용
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getUserId());
        parameters.put("user_pw", user.getUserPw());
        parameters.put("user_name", user.getUserName());
        parameters.put("user_email", user.getUserEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());

        return user;
    }

    @Override
    public Optional<User> findUserByUserId(String userId) throws UserNotFoundException {
        String sql = "SELECT id, user_id, user_pw, user_name, user_email FROM users WHERE user_id = ?";
        String[] params = new String[]{userId};
        int[] paramTypes = new int[]{Types.VARCHAR};

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, paramTypes, userRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public String updateUser(String userId, UpdatedUser updatedUser) throws UserNotFoundException {
        String sql = "UPDATE users SET user_pw = ?, user_name = ?, user_email = ? WHERE user_id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, updatedUser.getUserPw(), updatedUser.getUserName(),
                updatedUser.getUserEmail(), userId);

        if (rowsUpdated == 0) {
            throw new UserNotFoundException(userId); // 사용자를 찾지 못한 경우 예외 처리
        }
        return userId;
    }

    @Override
    public String deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
        return userId;
    }

    @Override
    public List<User> findAllUser() {
        String sql = "SELECT id, user_id, user_pw, user_name, user_email FROM users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserId(rs.getString("user_id"));
            user.setUserPw(rs.getString("user_pw"));
            user.setUserName(rs.getString("user_name"));
            user.setUserEmail(rs.getString("user_email"));
            return user;
        };
    }
}
