package codesquad.springcafe.users.repository;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.users.model.dto.UserCredentialDto;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.users.model.dto.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;



@Repository
public class H2UserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(H2UserRepository.class);
    private static final String USERID = "USERID";
    private static final String NAME = "NAME";
    private static final String EMAIL = "EMAIL";
    private static final String SALT = "SALT";
    private static final String HASHEDPASSWORD = "HASHEDPASSWORD";
    private static final String CREATIONDATE = "CREATIONDATE";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO USERS (USERID, EMAIL, NAME, SALT, HASHEDPASSWORD, CREATIONDATE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getEmail(), user.getName(), user.getSalt(), user.getHashedPassword(), user.getCreationDate().toString());
    }

    @Override
    public Optional<ArrayList<UserPreviewDto>> getAllUsers() {
        String sql = "SELECT USERID, NAME, EMAIL, CREATIONDATE FROM USERS";
        ArrayList<UserPreviewDto> userPreviews = (ArrayList<UserPreviewDto>) jdbcTemplate.query(sql, new UserPreviewRowMapper());
        return Optional.of(userPreviews);
    }

    @Override
    public Optional<UserPreviewDto> findUserById(String userId) {
        String sql = "SELECT NAME, EMAIL, CREATIONDATE FROM USERS WHERE USERID = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
            if (rs.next()) {
                String name = rs.getString(NAME);
                String email = rs.getString(EMAIL);
                String creationDate = rs.getString(CREATIONDATE);
                return Optional.of(new UserPreviewDto(userId, name, email, creationDate));
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<UserCredentialDto> getUserCredential(String userId) {
        String sql = "SELECT SALT, HASHEDPASSWORD FROM USERS WHERE USERID = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
            if (rs.next()) {
                return Optional.of(new UserCredentialDto(rs.getString(SALT), rs.getString(HASHEDPASSWORD)));
            }
            return Optional.empty();
        });
    }

    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        String sql = "UPDATE USERS SET EMAIL = ?, NAME = ?, PASSWORD = ? WHERE USERID = ?";
        jdbcTemplate.update(sql, updateData.getNewEmail(), updateData.getNewName(), updateData.getNewPassword(), userId);
    }

    private static class UserPreviewRowMapper implements org.springframework.jdbc.core.RowMapper<UserPreviewDto> {
        @Override
        public UserPreviewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            String userId = rs.getString(USERID);
            String name = rs.getString(NAME);
            String email = rs.getString(EMAIL);
            String creationDate = rs.getString(CREATIONDATE);
            return new UserPreviewDto(userId, name, email, creationDate);
        }
    }

}
