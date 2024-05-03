package codesquad.springcafe.database.article;

import codesquad.springcafe.model.Article;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleJdbcDatabase implements ArticleDatabase {
    private final JdbcTemplate jdbcTemplate;

    public ArticleJdbcDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article add(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("articles").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("write_date", article.getWriteDate());
        parameters.put("views", article.getViews());
        parameters.put("is_deleted", article.isDeleted());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        article.setId((key.longValue()));

        return article;
    }

    @Override
    public Optional<Article> findBy(Long id) {
        String sql = "select id, writer, title, content, write_date, views, is_deleted from articles where id = ? and is_deleted = false";
        List<Article> result = jdbcTemplate.query(sql, articleRowMapper(), id);
        return result.stream()
                .findAny();
    }

    @Override
    public List<Article> findAll(String nickname) {
        String sql = "SELECT id, writer, title, content, write_date, views, is_deleted FROM articles WHERE writer = ? and is_deleted = false";
        return jdbcTemplate.query(sql, articleRowMapper(), nickname);
    }

    @Override
    public List<Article> findAll() {
        String sql = "select id, writer, title, content, write_date, views, is_Deleted from articles where is_deleted=false";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public List<Article> findPageArticles(Long offset, int articlesPerPage) {
        String sql = "SELECT id, writer, title, content, write_date, views, is_Deleted FROM articles WHERE is_deleted = false ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, articleRowMapper(), articlesPerPage, offset);
    }


    @Override
    public void update(Article article) {
        String sql = "UPDATE articles SET title = ?, content = ?, writer = ?, views = ?, is_deleted = ? WHERE id = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), article.getWriter(), article.getViews()
                , article.isDeleted(), article.getId());
    }

    @Override
    public void increaseViews(Long id) {
        String sql = "UPDATE articles SET views = views + 1 WHERE id =?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void softDelete(Long id) {
        String sql = "UPDATE articles SET is_deleted = true WHERE id =?";
        jdbcTemplate.update(sql, id);
    }

//
//    @Override
//    public void deleteArticle(Long id) {
//        String sql = "DELETE FROM articles WHERE id = ?";
//        jdbcTemplate.updateArticle(sql, id);
//    }


    @Override
    public void clear() {
        String sql = "delete from articles";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Long> findAllId() {
        String sql = "SELECT id FROM articles WHERE is_deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"));
    }

    @Override
    public Long countTotalArticles() {
        String sql = "SELECT COUNT(id) FROM articles WHERE is_deleted = false";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {

            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String content = rs.getString("content");
            LocalDateTime writeDate = rs.getTimestamp("write_date").toLocalDateTime();
            Article article = new Article(writer, title, content, writeDate);
            article.setId(rs.getLong("id"));
            article.setViews(rs.getLong("views"));
            return article;
        };
    }

    @PostConstruct
    public void makeTestArticles() {
        long totalCount = countTotalArticles();
        for (long i = 1 + totalCount; i <= 100; i++) {
            Article article = new Article("상추", String.valueOf(i), String.valueOf(i), LocalDateTime.now());
            add(article);
        }
    }
}
