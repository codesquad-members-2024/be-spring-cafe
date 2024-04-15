package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static codesquad.springcafe.article.repository.ArticleConsts.DEFAULT_POINT;

@Repository
@Primary
public class H2ArticleRepository implements ArticleRepository {

    private final String ADD_SQL = "INSERT INTO ARTICLE (CREATEDAT, AUTHORID, TITLE, CONTENT, POINT) VALUES ( ?, ?, ?, ?, ?);";
    private final String FIND_BY_ID_SQL = "SELECT * FROM ARTICLE WHERE Id = ?";
    private final String FIND_BY_USER_SQL = "SELECT * FROM ARTICLE WHERE authorId = ?";
    private final String FIND_ALL_SQL = "SELECT * FROM article ORDER BY createdAt DESC;";

    private final String ADD_POINT_SQL = "UPDATE ARTICLE SET point = point + 1 WHERE id = ?;";

    private final String GET_USER_NAME = "SELECT NAME FROM USERS WHERE USERID = ?";
    private final String DELETE_ALL = "DELETE FROM ARTICLE";

    private final DataSource dataSource;

    @Autowired
    public H2ArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Timestamp createdDateTime = Timestamp.valueOf(LocalDateTime.now());
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(ADD_SQL)) {

            query.setTimestamp(1, createdDateTime);
            query.setString(2, simpleUserInfo.id());
            query.setString(3, articlePostReq.title());
            query.setString(4, articlePostReq.content());
            query.setInt(5, DEFAULT_POINT);

            query.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addArticle : " + e.getMessage());
        }
    }

    @Override
    public Article findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ID_SQL)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                List<Article> articles = rowToArticle(resultSet);
                return articles.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findById : " + e.getMessage());
        } catch (IndexOutOfBoundsException noMatchId) {
            return null;
        }
    }

    @Override
    public List<Article> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL_SQL)) {
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToArticle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findAllArticle : " + e.getMessage());
        }
    }

    @Override
    public List<Article> findByUserId(String id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_USER_SQL)) {
            query.setString(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToArticle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findById : " + e.getMessage());
        }
    }

    @Override
    public void addPoint(Article article) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(ADD_POINT_SQL)) {
            query.setInt(1, article.getId());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": update : " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(DELETE_ALL)) {

            query.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": update : " + e.getMessage());
        }
    }

    private List<Article> rowToArticle(ResultSet resultSet) throws SQLException {
        List<Article> articles = new ArrayList<>();

        while (resultSet.next()) {
            articles.add(new Article(
                    resultSet.getInt("id"),
                    resultSet.getTimestamp("createdAt"),
                    new SimpleUserInfo(resultSet.getString("authorId"), getUserName(resultSet.getString("authorId"))),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getInt("point")
            ));
        }
        return articles;
    }

    private String getUserName(String id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement query = connection.prepareStatement(GET_USER_NAME)) {
            query.setString(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                if(resultSet.next()) return resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": getName : " + e.getMessage());
        }

        return null;
    }
}
