package codesquad.springcafe.repository.article;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.dto.UpdatedArticle;
import codesquad.springcafe.exception.ArticleNotFoundException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleJdbcRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // Spring 프레임워크에서 제공하는 JDBC 추상화 계층
    }

    @Override
    public Article addArticle(Article article) {
        // 단순한 삽입 작업이므로 SimpleJdbcInsert 사용
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("creation_time", Timestamp.valueOf(article.getCreationTime()));
        parameters.put("view_count", article.getViewCount());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());

        logger.debug(article + " JdbcDB 저장 완료");
        return article;
    }

    @Override
    public Article findArticleById(long id) throws Exception {
        String sql = "SELECT id, writer, title, content, creation_time, view_count FROM article WHERE id = ?";
        Long[] params = new Long[]{id};
        int[] paramTypes = new int[]{Types.BIGINT};

        try {
            return jdbcTemplate.queryForObject(sql, params, paramTypes, articleRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException(id);
        }
    }

    @Override
    public long modifyArticle(long id, UpdatedArticle article) throws Exception {
        String sql = "UPDATE article SET title = ?, content = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, article.getTitle(), article.getContent(), id);

        if (rowsUpdated == 0) {
            throw new ArticleNotFoundException(id); // 게시글을 찾지 못한 경우 예외 처리
        }
        return id;
    }

    @Override
    public long deleteArticle(long id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "SELECT id, writer, title, content, creation_time, view_count FROM article";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    @Override
    public long increaseViewCount(long id) throws Exception {
        String sql = "UPDATE article SET view_count = view_count + 1 WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, id);
        if (rowsUpdated == 0) {
            throw new ArticleNotFoundException(id); // 게시글을 찾지 못한 경우 예외 처리
        }
        return id;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("id"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("creation_time").toLocalDateTime(),
                rs.getLong("view_count")
        );
    }
}
