package codesquad.springcafe.users.repository;

import codesquad.springcafe.exception.PasswordMismatchException;
import model.user.User;
import model.user.UserData;
import model.user.UserUpdateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Primary
@Repository
public class H2UserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(H2UserRepository.class);

    private final DataSource dataSource;

    @Autowired
    public H2UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createUser(UserData userData) {
        String userId = userData.getUserId();
        String email = userData.getEmail();
        String name = userData.getName();
        String password = userData.getPassword();

        User user = new User(userId, email, name, password);
        logger.debug("User Created : {}", user);

        String sql = "INSERT INTO USERS (USERID, EMAIL, NAME, PASSWORD, CREATIONDATE) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getCreationDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String userId = rs.getString("userId");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String creationDate = rs.getString("creationDate");
                User user = new User(userId, email, name, password, creationDate);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return users;
    }

    @Override
    public User findUserById(String userId) {
        User user = null;
        String sql = "SELECT * FROM USERS WHERE userId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String creationDate = rs.getString("creationDate");
                    user = new User(userId, email, name, password, creationDate);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return user;
    }

    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        User user = findUserById(userId);
        validatePassword(user.getPassword(), updateData.getCurrentPassword());

        // 사용자 정보 업데이트
        user.updateUser(updateData);

        // 데이터베이스에 업데이트된 정보 반영
        String sql = "UPDATE USERS SET EMAIL = ?, NAME = ?, PASSWORD = ? WHERE USERID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, userId);
            pstmt.executeUpdate();

            logger.debug("User Updated : {}", user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private void validatePassword(String userPassword, String inputPassword) {
        if (!userPassword.equals(inputPassword)) {
            throw new PasswordMismatchException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }
}
