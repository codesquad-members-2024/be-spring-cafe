package codesquad.springcafe.comment.repository;

import codesquad.springcafe.comment.DTO.Comment;
import codesquad.springcafe.comment.DTO.CommentPostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class H2CommentRepository implements CommentRepository {

    private final String ADD_COMMENT = "INSERT INTO comment (ARTICLEID, CREATEDAT, AUTHOR, AUTHORID, CONTENT) VALUES (?, ?, ?, ?, ?);";
    private final String FIND_BY_ARTICLE_ID = "SELECT * FROM comment WHERE ArticleId = ? ORDER BY createdAt DESC;";
    private final String FIND_BY_USER_ID = "SELECT * FROM comment WHERE AuthorId = ? ORDER BY createdAt DESC;";

    private final DataSource dataSource;

    @Autowired
    public H2CommentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Timestamp createdDateTime = Timestamp.valueOf(LocalDateTime.now());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(ADD_COMMENT)) {

            query.setInt(1, commentPostReq.articleId());
            query.setTimestamp(2, createdDateTime);
            query.setString(3, simpleUserInfo.name());
            query.setString(4, simpleUserInfo.id());
            query.setString(5, commentPostReq.content());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addComment : " + e.getMessage());
        }
    }

    @Override
    public List<Comment> findByArticleId(int articleId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ARTICLE_ID)) {
            query.setInt(1, articleId);
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToComment(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByArticleId : " + e.getMessage());
        }
    }

    @Override
    public List<Comment> findByUserId(String userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_USER_ID)) {
            query.setString(1, userId);
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToComment(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        }
    }

    private List<Comment> rowToComment(ResultSet resultSet) throws SQLException {
        List<Comment> comments = new ArrayList<>();

        while (resultSet.next()) {
            comments.add(new Comment(
                            resultSet.getInt("id"),
                            resultSet.getInt("articleId"),
                            resultSet.getString("content"),
                            resultSet.getTimestamp("createdAt"),
                            resultSet.getString("author"),
                            resultSet.getString("authorId")
                    )
            );
        }
        return comments;
    }
}
