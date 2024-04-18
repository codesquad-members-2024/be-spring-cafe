package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJDBC implements UserRepository{
    private final DataSource dataSource;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public UserRepositoryJDBC(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (email, userId, password, signUpDate) VALUES (?, ?, ?, ?)";
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUserId());
            pstmt.setString(3, user.getPassword());
            pstmt.setDate(4, java.sql.Date.valueOf(user.getSignUpDate()));

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET email=? WHERE userid=?";
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getUserId());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select * from users where userid = ?";
        return getUser(id, sql);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from users where email = ?";
        return getUser(email, sql);
    }

    private Optional<User> getUser(String email, String sql) {
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userid"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<>();
            while(rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userid"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSignUpDate(rs.getDate("signUpDate").toLocalDate());
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
