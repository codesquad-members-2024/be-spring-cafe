package codesquad.springcafe.user.respository.util;


import codesquad.springcafe.user.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("userId"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"));
    }
}
