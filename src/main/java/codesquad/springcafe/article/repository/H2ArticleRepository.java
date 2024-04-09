package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class H2ArticleRepository implements ArticleRepository {

    private final DataSource dataSource;

    public H2ArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Article article) throws IllegalArgumentException {
        String sql = "INSERT INTO ARTICLE (CREATED_DATETIME, AUTHOR, TITLE, CONTENT, POINT) " +
                "VALUES (?, ?, ?, ?, ?);";

        article.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setTimestamp(1, article.getCreated_datetime());
            query.setString(2, article.getAuthor());
            query.setString(3, article.getTitle());
            query.setString(4, article.getContent());
            query.setInt(5, article.getPoint());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addArticle : " + e.getMessage());
        }
    }

    @Override
    public Article findById(int id) {
        String sql = "SELECT * FROM ARTICLE WHERE Id = ?";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                List<Article> articles = rowToArticle(resultSet);
                return articles.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM ARTICLE";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToArticle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        }
    }

    @Override
    public void addPoint(Article article) {
        String sql = "UPDATE ARTICLE SET point = point + 1 WHERE id = ?;";

        try (PreparedStatement query = dataSource.getConnection().prepareStatement(sql)) {
            query.setInt(1, article.getId());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": update : " + e.getMessage());
        }
    }

    private List<Article> rowToArticle(ResultSet resultSet) throws SQLException {
        List<Article> articles = new ArrayList<>();

        while (resultSet.next()) {
            articles.add(new Article(
                            resultSet.getString("author"),
                            resultSet.getString("title"),
                            resultSet.getString("content")
                            )
                    .completeArticle(
                            resultSet.getInt("id"),
                            resultSet.getTimestamp("created_datetime"),
                            resultSet.getInt("point")
            ));
        }
        return articles;
    }
}
