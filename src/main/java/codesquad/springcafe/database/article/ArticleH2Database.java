package codesquad.springcafe.database.article;

import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ArticleH2Database implements ArticleDatabase {
    private final JdbcTemplate jdbcTemplate;

    public ArticleH2Database(DataSource dataSource) {
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
        parameters.put("writeDate", article.getWriteDate());
        parameters.put("views", article.getViews());
        parameters.put("isDeleted", article.isDeleted());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        article.setId((key.longValue()));

        return article;
    }

    @Override
    public Optional<Article> findBy(Long id) {
        String sql = "select id, writer, title, content, writedate, views, isDeleted from articles where id = ? and isDeleted = false";
        List<Article> result = jdbcTemplate.query(sql, articleRowMapper(), id);
        return result.stream()
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        String sql = "select id, writer, title, content, writedate, views, isDeleted from articles where isDeleted=false";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public void update(Article article) {
        String sql = "UPDATE articles SET title = ?, content = ?, writer = ?, views = ?, isDeleted = ? WHERE id = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), article.getWriter(), article.getViews()
                , article.isDeleted(), article.getId());
    }
//
//    @Override
//    public void delete(Long id) {
//        String sql = "DELETE FROM articles WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }


    @Override
    public void clear() {
        String sql = "delete from articles";
        jdbcTemplate.update(sql);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {

            String writer = rs.getString("writer");
            String title = rs.getString("title");
            String content = rs.getString("content");
            LocalDateTime writeDate = rs.getTimestamp("writeDate").toLocalDateTime();
            Article article = new Article(writer, title, content, writeDate);
            article.setId(rs.getLong("id"));
            article.setViews(rs.getLong("views"));
            return article;
        };
    }
}
