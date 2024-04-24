package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository() {
        this.jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO `article` (id, timestamp, writer, title, content) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            article.getId(), Timestamp.valueOf(article.getTimeStamp()), article.getWriter(), article.getTitle(), article.getContent());
    }

    @Override
    public List<Article> getAll() {
        String sql = "SELECT * FROM `article`";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("id"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content")
            );
            return article;
        });
    }

    @Override
    public Optional<Article> getById(Long id) {
        String sql = "SELECT * FROM `article` WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("id"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content")
            );
            return Optional.ofNullable(article);
        });
    }
}