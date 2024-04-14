package codesquad.springcafe.users.repository;

import model.user.User;
import model.user.dto.UserCredentialDto;
import model.user.dto.UserPreviewDto;
import model.user.dto.UserUpdateData;
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
import java.util.Optional;


@Primary
@Repository
public class H2UserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(H2UserRepository.class);

    private final DataSource dataSource;

    @Autowired
    public H2UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createUser(User user) {
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
    public Optional<ArrayList<UserPreviewDto>> getAllUsers() {
        ArrayList<UserPreviewDto> userPreviews = new ArrayList<>();
        String sql = "SELECT USERID, NAME, EMAIL, CREATIONDATE FROM USERS";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String userId = rs.getString("userId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String creationDate = rs.getString("creationDate");

                UserPreviewDto userPreviewDto = new UserPreviewDto(userId, name, email, creationDate);

                userPreviews.add(userPreviewDto);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return Optional.of(userPreviews);
    }

    @Override
    public Optional<UserPreviewDto> findUserById(String userId) {
        UserPreviewDto userPreviewDto = null;
        String sql = "SELECT NAME, EMAIL, CREATIONDATE FROM USERS WHERE userId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String creationDate = rs.getString("creationDate");
                    userPreviewDto = new UserPreviewDto(userId, name, email, creationDate);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return Optional.ofNullable(userPreviewDto);
    }

    @Override
    public Optional<UserCredentialDto> getUserCredential(String userId) {
        UserCredentialDto userCredentialDto = null;
        String sql = "SELECT PASSWORD FROM USERS WHERE userId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userCredentialDto = new UserCredentialDto(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return Optional.ofNullable(userCredentialDto);
    }

    @Override
    public void updateUser(String userId, UserUpdateData updateData) {
        String sql = "UPDATE USERS SET EMAIL = ?, NAME = ?, PASSWORD = ? WHERE USERID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updateData.getNewEmail());
            pstmt.setString(2, updateData.getNewName());
            pstmt.setString(3, updateData.getNewPassword());
            pstmt.setString(4, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


}
