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
    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO article (index, timestamp, writer, title, content) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            article.getIndex(), Timestamp.valueOf(article.getTimeStamp()), article.getWriter(), article.getTitle(), article.getContent());
    }

    @Override
    public List<Article> getAll() {
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("index"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content")
            );
            return article;
        });
    }

    @Override
    public Optional<Article> getByIndex(Long index) {
        String sql = "SELECT * FROM article WHERE index = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{index}, (resultSet, rowNum) -> {
            Article article = new Article(
                resultSet.getLong("index"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("content")
            );
            return Optional.ofNullable(article);
        });
    }
}