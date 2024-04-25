package springcafe.user.repository;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springcafe.user.exception.UserNotFoundException;
import springcafe.user.model.User;

import java.util.List;


@Repository
public class H2UserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public H2UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(User user) {
        jdbcTemplate.update("INSERT into USERS (USERID, PASSWORD, NAME, EMAIL) values (?,?,?,?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

    }


    @Override
    public User findById(String userId) {

        try {

            User user = jdbcTemplate.queryForObject(
                    "SELECT USERID, NAME, EMAIL, PASSWORD, ID FROM USERS WHERE USERID =?",
                    new Object[]{userId},
                    (rs, rowNum) -> new User(
                            rs.getString("USERID"),
                            rs.getString("NAME"),
                            rs.getString("EMAIL"),
                            rs.getString("PASSWORD"),
                            rs.getLong("ID")
                            )
            );

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT USERID, NAME, EMAIL, ID FROM USERS",
                (rs, rowNum) -> new User(rs.getString("USERID"),
                        rs.getString("NAME"), rs.getString("EMAIL"), rs.getLong("ID"))
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE USERS SET NAME = ?, EMAIL = ? WHERE USERID = ?",
                user.getName(), user.getEmail(), user.getUserId());

    }

    @Override
    public boolean checkDuplicateId(String userId) {
        try {
            jdbcTemplate.queryForObject(
                    "SELECT USERID FROM USERS WHERE USERID = ?",
                    new Object[]{userId},
                    String.class
            );
            return true;  //중복 아이디임
        } catch (EmptyResultDataAccessException e) {
            return false; //중복 아이디가 아님
        }

    }

}
