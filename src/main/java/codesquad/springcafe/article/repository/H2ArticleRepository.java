package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
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

import static codesquad.springcafe.article.repository.ArticleConsts.DEFAULT_POINT;

@Repository
@Primary
public class H2ArticleRepository implements ArticleRepository {

    private final String ADD_SQL = "INSERT INTO ARTICLE (CREATEDAT, AUTHOR, AUTHORID, TITLE, CONTENT, POINT) VALUES (?, ?, ?, ?, ?, ?);";
    private final String FIND_BY_ID_SQL = "SELECT * FROM ARTICLE WHERE Id = ?";
    private final String FIND_ALL_SQL = "SELECT * FROM ARTICLE";
    private final String ADD_POINT_SQL = "UPDATE ARTICLE SET point = point + 1 WHERE id = ?;";

    private final DataSource dataSource;

    public H2ArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ArticlePostReq articlePostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Timestamp  createdDateTime =Timestamp.valueOf(LocalDateTime.now());
        try (PreparedStatement query = dataSource.getConnection().prepareStatement(ADD_SQL)) {
            query.setTimestamp(1, createdDateTime);
            query.setString(2, simpleUserInfo.name());
            query.setString(3, simpleUserInfo.id());
            query.setString(4, articlePostReq.title());
            query.setString(5, articlePostReq.content());
            query.setInt(6, DEFAULT_POINT);

            query.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(this.getClass() + ": addArticle : " + e.getMessage());
        }
    }

    @Override
    public Article findById(int id) {
        try (PreparedStatement query = dataSource.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            query.setInt(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                List<Article> articles = rowToArticle(resultSet);
                return articles.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findById : " + e.getMessage());
        }catch (IndexOutOfBoundsException noMatchId){
            return null;
        }
    }

    @Override
    public List<Article> findAll() {
        try (PreparedStatement query = dataSource.getConnection().prepareStatement(FIND_ALL_SQL)) {
            try (ResultSet resultSet = query.executeQuery()) {
                return rowToArticle(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(this.getClass() + ": findByUserId : " + e.getMessage());
        }
    }

    @Override
    public void addPoint(Article article) {
        try (PreparedStatement query = dataSource.getConnection().prepareStatement(ADD_POINT_SQL)) {
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
                    resultSet.getInt("id"),
                    resultSet.getTimestamp("createdAt"),
                    new SimpleUserInfo(resultSet.getString("authorId"), resultSet.getString("author")),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getInt("point")
            ));
        }
        return articles;
    }
}
