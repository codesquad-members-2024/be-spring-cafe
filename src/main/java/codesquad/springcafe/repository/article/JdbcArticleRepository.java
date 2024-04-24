package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createArticle(Article article) {
        String SQL = "INSERT INTO article (writer, title, content, views, created) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, article.getWriter(), article.getTitle(), article.getContent(), article.getViews(),
                article.getCreated());
    }

    @Override
    public List<Article> findAllArticles() {
        String SQL = "SELECT * FROM article";
        return jdbcTemplate.query(SQL, rowMapper());
    }

    @Override
    public Article findById(long id) {
        String SQL = "SELECT * FROM article WHERE id = ?";
        return jdbcTemplate.queryForObject(SQL, rowMapper(), id);
    }

    @Override
    public void updateViews(long id) {
        String SQL = "UPDATE article SET views = views+1 WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public void updateArticle(long id, ArticleDto articleDto) {
        String SQL = "UPDATE article SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(SQL, articleDto.getTitle(), articleDto.getContent(), id);
    }

    @Override
    public void deleteArticle(long id) {
        String SQL = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }

    private RowMapper<Article> rowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String content = rs.getString("content");
            long views = rs.getLong("views");
            LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
            return new Article(id, writer, title, content, views, created);
        };
    }
}