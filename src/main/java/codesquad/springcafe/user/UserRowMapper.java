package codesquad.springcafe.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new UserBuilder()
            .userId(resultSet.getString("userid"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .password(resultSet.getString("password"))
            .build();
    }

}
