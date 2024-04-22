package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.exception.ArticleNotFountException;
import codesquad.springcafe.model.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class ArticleJdbcRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("article_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("local_date_time", article.getLocalDateTime());
        parameters.put("hits", article.getHits());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        article.setArticleId((key.longValue()));

        return article.getArticleId();
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        String sql = "SELECT article_id, writer, title, contents, local_date_time, hits FROM articles WHERE article_id = ?";
        try {
            Article article = jdbcTemplate.queryForObject(sql, articleRowMapper(), articleId);
            return Optional.ofNullable(article);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFountException();
        }
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "SELECT article_id, writer, title, contents, local_date_time, hits FROM articles";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM articles";
        jdbcTemplate.update(sql);
    }

    @Override
    public void update(Long articleId, ArticleRequestDto articleRequestDto) {
        String sql = "UPDATE articles SET title = ?, contents = ? WHERE article_id = ?";
        jdbcTemplate.update(sql, articleRequestDto.getTitle(), articleRequestDto.getContents(), articleId);
    }

    @Override
    public void delete(Long articleId) {
        String sql = "DELETE FROM articles WHERE article_id = ?";
        jdbcTemplate.update(sql, articleId);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            long articleId = rs.getLong("article_id");
            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            LocalDateTime localDateTime = rs.getTimestamp("local_date_time").toLocalDateTime();
            long hits = rs.getLong("hits");

            return new Article(articleId, writer, title, contents, localDateTime, hits);
        };
    }

}
