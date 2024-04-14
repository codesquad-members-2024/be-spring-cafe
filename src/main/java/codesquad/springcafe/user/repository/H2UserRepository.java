package codesquad.springcafe.user.repository;

import codesquad.springcafe.user.DTO.UserListRes;
import codesquad.springcafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class H2UserRepository implements UserRepository {
    private final String FIND_ALL_USER = "SELECT * FROM USERS";
    private final String FIND_BY_ID_USER = "SELECT * FROM USERS WHERE UserId = ?";
    private final String UPDATE_USER = "UPDATE Users SET email = ?, name = ? WHERE userId = ?;";
    private final String ADD_USER = "INSERT INTO Users (userId, password, name, email) VALUES (?, ?, ?, ?);";

    private final DataSource dataSource;

    @Autowired
    public H2UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addUser(User user) throws IllegalArgumentException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(ADD_USER)) {
            query.setString(1, user.getUserId());
            query.setString(2, user.getPassword());
            query.setString(3, user.getName());
            query.setString(4, user.getEmail());

            query.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException alreadyExistsId) {
            throw new IllegalArgumentException(alreadyExistsId);

        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addUser : " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(UPDATE_USER)) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ID_USER)) {
            query.setString(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                List<User> users = rowToUser(resultSet);
                return users.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        } catch (IndexOutOfBoundsException userNotFound) {
            return null;
        }
    }

    @Override
    public List<UserListRes> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL_USER)) {
            try (ResultSet resultSet = query.executeQuery()) {
                List<User> users = rowToUser(resultSet);
                return getUserList(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findAll: " + e.getMessage());
        }
    }

    private List<UserListRes> getUserList(List<User> users) {
        List<UserListRes> userListRes = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userListRes.add(new UserListRes(i + 1, user.getUserId(), user.getName(), user.getEmail()));
        }

        return userListRes;
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
