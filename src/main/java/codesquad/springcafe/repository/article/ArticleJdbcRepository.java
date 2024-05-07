package codesquad.springcafe.repository.article;

import codesquad.springcafe.exception.db.ArticleNotFoundException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.ListArticle;
import codesquad.springcafe.model.UpdatedArticle;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        parameters.put("user_id", article.getUserId());
        parameters.put("title", article.getTitle());
        parameters.put("content", article.getContent());
        parameters.put("is_modified", article.isModified());
        parameters.put("is_deleted", article.isModified());
        parameters.put("creation_time", Timestamp.valueOf(article.getCreationTime()));
        parameters.put("modified_time", null);
        parameters.put("view_count", article.getViewCount());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());

        logger.debug(article + " JdbcDB 저장 완료");
        return article;
    }

    @Override
    public Optional<Article> findArticleById(long id) throws ArticleNotFoundException {
        String sql = "SELECT id, user_id, title, content, is_modified, is_deleted, creation_time, modified_time, view_count FROM article WHERE id = ?";
        Long[] params = new Long[]{id};
        int[] paramTypes = new int[]{Types.BIGINT};

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, paramTypes, articleRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException(id);
        }
    }

    @Override
    public boolean isArticleDeleted(long id) throws ArticleNotFoundException {
        String sql = "SELECT is_deleted FROM article WHERE id = ?";
        Long[] params = new Long[]{id};
        int[] paramTypes = new int[]{Types.BIGINT};

        try {
            return Boolean.TRUE.equals(
                    jdbcTemplate.queryForObject(sql, params, paramTypes, (rs, rowNum) -> rs.getBoolean("is_deleted")));
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException(id);
        }
    }

    @Override
    public long modifyArticle(long id, UpdatedArticle article) throws ArticleNotFoundException {
        String sql = "UPDATE article SET title = ?, content = ?, is_modified = TRUE, modified_time = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, article.getTitle(), article.getContent(),
                Timestamp.valueOf(article.getModifiedTime()), id);

        if (rowsUpdated == 0) {
            throw new ArticleNotFoundException(id); // 게시글을 찾지 못한 경우 예외 처리
        }
        return id;
    }

    @Override
    public long deleteArticle(long id) {
        String sql = "UPDATE article SET is_deleted = TRUE WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, id);

        if (rowsUpdated == 0) {
            throw new ArticleNotFoundException(id); // 게시글을 찾지 못한 경우 예외 처리
        }
        return id;
    }

    @Override
    public List<ListArticle> findAllArticle() {
        String sql = "SELECT id, user_id, title, is_modified, creation_time, modified_time, view_count FROM article WHERE is_deleted = FALSE";
        return jdbcTemplate.query(sql, listArticleRowMapper());
    }

    @Override
    public long increaseViewCount(long id) throws ArticleNotFoundException {
        String sql = "UPDATE article SET view_count = view_count + 1 WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, id);
        if (rowsUpdated == 0) {
            throw new ArticleNotFoundException(id); // 게시글을 찾지 못한 경우 예외 처리
        }
        return id;
    }

    @Override
    public List<Long> findUserArticleIds(String userId) {
        String sql = "SELECT id FROM article WHERE user_id = ?";
        String[] params = new String[]{userId};
        int[] paramTypes = new int[]{Types.VARCHAR};

        try {
            return jdbcTemplate.query(sql, params, paramTypes, articleIdRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    private RowMapper<ListArticle> listArticleRowMapper() {
        return (rs, rowNum) -> new ListArticle(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("title"),
                rs.getBoolean("is_modified"),
                rs.getTimestamp("creation_time").toLocalDateTime(),
                rs.getTimestamp("modified_time") != null ? rs.getTimestamp("modified_time").toLocalDateTime() : null,
                rs.getLong("view_count")
        );
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> new Article(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getBoolean("is_modified"),
                rs.getBoolean("is_deleted"),
                rs.getTimestamp("creation_time").toLocalDateTime(),
                rs.getTimestamp("modified_time") != null ? rs.getTimestamp("modified_time").toLocalDateTime() : null,
                rs.getLong("view_count")
        );
    }

    private RowMapper<Long> articleIdRowMapper() {
        return (rs, rowNum) -> rs.getLong("id");
    }
}
