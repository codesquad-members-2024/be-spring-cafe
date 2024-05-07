package codesquad.springcafe.domain.comment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comment").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", comment.getUserId());
        parameters.put("article_id", comment.getArticleId());
        parameters.put("writer", comment.getWriter());
        parameters.put("content", comment.getContent());
        parameters.put("deleted", comment.isDeleted());
        parameters.put("currentTime", comment.getCurrentTime());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    public void deleteByCommentId(Long commentId) {
        jdbcTemplate.update("update comment set deleted = ? where id = ?", true, commentId);
    }

    public void deleteByArticleId(Long articleId) {
        jdbcTemplate.update("update comment set deleted = ? where article_id = ?", true, articleId);
    }

    public List<Comment> findAllByArticleId(Long id) {
        return jdbcTemplate.query("select * from comment where article_id = ?", commentRowMapper(), id);
    }

    public RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment(
                    rs.getLong("user_id"),
                    rs.getLong("article_id"),
                    rs.getString("writer"),
                    rs.getString("content"),
                    rs.getTimestamp("currentTime").toLocalDateTime()
            );
            comment.setId(rs.getLong("id"));
            comment.setDeleted(rs.getBoolean("deleted"));
            return comment;
        };
    }
}
