package codesquad.springcafe.domain.comment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
        parameters.put("currentTime", comment.getCurrentTime());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
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
            return comment;
        };
    }
}
