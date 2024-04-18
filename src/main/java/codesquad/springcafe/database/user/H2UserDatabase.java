package codesquad.springcafe.database.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UpdatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("H2UserDatabase")
public class H2UserDatabase implements UserDatabase{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2UserDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // 스프링의 JDBC 추상화 계층
    }

    // RowMapper
    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> new User(
                rs.getString("user_id"),
                rs.getString("user_nickname"),
                rs.getString("user_email"),
                rs.getString("user_password"));
    }

    @Override
    public void saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getId());
        parameters.put("user_password", user.getPassword());
        parameters.put("user_nickname", user.getNickname());
        parameters.put("user_email", user.getEmail());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public void updateUser(String id, UpdatedUser updatedUser) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.update(updatedUser);

            jdbcTemplate.update("update users set user_password = ?, user_nickname = ?, user_email = ? where user_id = ?",
                    user.getPassword(), user.getNickname(), user.getEmail(), id);

//            jdbcTemplate.update("update users set user_password = ?, user_nickname = ?, user_email = ? where user_id = ?",
//                    updatedUser.getNewPassword(), updatedUser.getNewNickname(), updatedUser.getNewEmail(), id);

        } else {
            throw new IllegalArgumentException("User id: " + id + " not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    @Override
    public Optional<User> getUserById(String userId) { // userId로 회원 찾기
        List<User> userList = jdbcTemplate.query("select * from users where user_id = ?", userRowMapper(), userId);
        return Optional.ofNullable(userList.get(0));
    }

    @Override
    public int getUsersSize() {
        Integer size = jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        return size != null ? size : 0;
    }

    // 아직 사용하지 않는 기능
//    public void deleteUser(String userId) {
//        jdbcTemplate.update("delete from users where user_id = ?", userId);
//    }

}
