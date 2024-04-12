package codesquad.springcafe.user.repository;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class H2UserRepository implements UserRepository {
    private final DataSource dataSource;

    public H2UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addUser(User user) throws IllegalArgumentException {
        String sql = "INSERT INTO Users (userId, password, name, email) VALUES (?, ?, ?, ?);";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setString(1, user.getUserId());
            query.setString(2, user.getPassword());
            query.setString(3, user.getName());
            query.setString(4, user.getEmail());

            query.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException alreadyExistsId){
            throw new IllegalArgumentException(alreadyExistsId);

        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addUser : " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE Users SET email = ?, name = ? WHERE userId = ?;";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getName());
            query.setString(3, user.getUserId());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": update : " + e.getMessage());
        }
    }

    @Override
    public User findUserById(String id) {
        String sql = "SELECT * FROM USERS WHERE UserId = ?";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setString(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                List<User> users = rowToUser(resultSet);
                return users.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        } catch (IndexOutOfBoundsException userNotFound){
            return null;
        }
    }

    @Override
    public List<UserDTO> findAll() {
        String sql = "SELECT * FROM USERS";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            try (ResultSet resultSet = query.executeQuery()) {
                List<User> users = rowToUser(resultSet);
                return getUserDTOS(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findAll: " + e.getMessage());
        }
    }

    private List<UserDTO> getUserDTOS(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userDTOS.add(new UserDTO(i + 1, user.getUserId(), user.getName(), user.getEmail()));
        }

        return userDTOS;
    }

    private List<User> rowToUser(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            users.add(new User(resultSet.getString("userId"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("email")));
        }
        return users;
    }
}
