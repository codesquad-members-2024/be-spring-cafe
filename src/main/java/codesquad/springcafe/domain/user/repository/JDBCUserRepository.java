package codesquad.springcafe.domain.user.repository;

import codesquad.springcafe.domain.user.DTO.UserListRes;
import codesquad.springcafe.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class JDBCUserRepository implements UserRepository {
    private final String FIND_ALL_USER = "SELECT * FROM USERS";
    private final String FIND_BY_ID_USER = "SELECT * FROM USERS WHERE UserId = ?";
    private final String UPDATE_USER = "UPDATE Users SET name = ?, email = ?, password = ? WHERE userId = ?;";
    private final String ADD_USER = "INSERT INTO Users (userId, password, name, email) VALUES (?, ?, ?, ?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(User user) throws IllegalArgumentException {
        try {
            String[] args = new String[]{user.getUserId(), user.getPassword(), user.getName(), user.getEmail()};
            int[] pramTypes = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
            jdbcTemplate.update(ADD_USER, args, pramTypes);

        } catch (DuplicateKeyException alreadyExistsId) {
            throw new IllegalArgumentException(alreadyExistsId);
        }
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getName(), user.getEmail(), user.getPassword(), user.getUserId());
    }

    @Override
    public User findUserById(String id) {
        try {
            String[] args = new String[]{id};
            int[] pramTypes = new int[]{Types.VARCHAR};
            return jdbcTemplate.query(FIND_BY_ID_USER, args, pramTypes, userRowMapper()).get(0);
        } catch (IndexOutOfBoundsException userNotFound) {
            return null;
        }
    }

    @Override
    public List<UserListRes> findAll() {
        return getUserList(jdbcTemplate.query(FIND_ALL_USER, userRowMapper()));
    }

    private List<UserListRes> getUserList(List<User> users) {
        List<UserListRes> userListRes = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userListRes.add(new UserListRes(i + 1, user.getUserId(), user.getName(), user.getEmail()));
        }

        return userListRes;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

    }
}
