package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryJDBC implements PostRepository {

    private final DataSource dataSource;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public PostRepositoryJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO post (title, content, createdAt) VALUES (?, ?, ?)";
        try{
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setDate(3, java.sql.Date.valueOf(post.getCreatedAt()));
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Post> findById(Long postId) {
        String sql = "select * from post where id = ?";

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(postId));

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getDate("createdAt").toLocalDate());

                return Optional.of(post);
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
    public List<Post> findAll() {
        String sql = "select * from post";
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            rs = pstmt.executeQuery();

            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getDate("createdAt").toLocalDate());
                posts.add(post);
            }
            return posts;
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
