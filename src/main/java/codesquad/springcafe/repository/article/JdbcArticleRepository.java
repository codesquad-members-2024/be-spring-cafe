package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.ArticleNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String SQL = "INSERT INTO article (writer, title, content, views, createdDate, lastModifiedDate) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, article.getWriter(), article.getTitle(), article.getContent(), article.getViews(),
                article.getCreatedDate(), article.getLastModifiedDate());
    }

    @Override
    public List<Article> findAllArticles() {
        String SQL = "SELECT * FROM article";
        return jdbcTemplate.query(SQL, rowMapper());
    }

    @Override
    public Optional<Article> findById(long id) {
        String SQL = "SELECT * FROM article WHERE id = ?";
        try {
            Article article = jdbcTemplate.queryForObject(SQL, rowMapper(), id);
            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateViews(long id) {
        String SQL = "UPDATE article SET views = views+1 WHERE id = ?";
        int update = jdbcTemplate.update(SQL, id);
        if (update == 0) {
            throw new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다.");
        }
    }

    @Override
    public void updateArticle(long id, ArticleDto articleDto) {
        String SQL = "UPDATE article SET title = ?, content = ? WHERE id = ?";
        int update = jdbcTemplate.update(SQL, articleDto.getTitle(), articleDto.getContent(), id);
        if (update == 0) {
            throw new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다.");
        }
    }

    @Override
    public void deleteArticle(long id) {
        String SQL = "DELETE FROM article WHERE id = ?";
        int update = jdbcTemplate.update(SQL, id);
        if (update == 0) {
            throw new ArticleNotFoundException(id + " ID 게시글이 존재하지 않습니다.");
        }
    }

    private RowMapper<Article> rowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String content = rs.getString("content");
            long views = rs.getLong("views");
            LocalDateTime createdDate = rs.getTimestamp("createdDate").toLocalDateTime();
            LocalDateTime lastModifiedDate = rs.getTimestamp("lastModifiedDate").toLocalDateTime();
            return new Article(id, writer, title, content, views, createdDate, lastModifiedDate);
        };
    }
}