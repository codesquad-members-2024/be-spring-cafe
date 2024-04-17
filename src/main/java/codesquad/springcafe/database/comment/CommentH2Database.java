package codesquad.springcafe.database.comment;

import codesquad.springcafe.model.Comment;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class CommentH2Database implements CommentDatabase {
    private final JdbcTemplate jdbcTemplate;

    public CommentH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Comment add(Comment comment) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("comments").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("writer", comment.getWriter());
        params.put("content", comment.getContent());
        params.put("writeDate", comment.getWriteDate());
        params.put("articleId", comment.getArticleId());
        params.put("isDeleted", comment.isDeleted());

        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        comment.setId(key.longValue());

        return comment;
    }

    @Override
    public Optional<Comment> findBy(Long id) {
        String sql = "SELECT id, writer, content, writeDate, articleId, isDeleted FROM comments WHERE id = ? and isDeleted=false";
        return jdbcTemplate.query(sql, commentRowMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public List<Comment> findAll(Long articleId) {
        String sql = "SELECT id, writer, content, writeDate, articleId, isDeleted FROM comments WHERE articleId = ? and isDeleted=false";
        return jdbcTemplate.query(sql, commentRowMapper(), articleId);
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET writer = ?, content = ?, writeDate = ?, articleId = ?, isDeleted = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getWriter(), comment.getContent(), comment.getWriteDate(),
                comment.getArticleId(), comment.isDeleted(), comment.getId());
    }
//
//    @Override
//    public void delete(Long id) {
//        String sql = "DELETE FROM comments WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }

    @Override
    public void clear() {
        String sql = "DELETE FROM comments";
        jdbcTemplate.update(sql);
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            String writer = rs.getString("writer");
            String content = rs.getString("content");
            long articleId = rs.getLong("articleId");
            LocalDateTime writeDate = rs.getTimestamp("writeDate").toLocalDateTime();

            Comment comment = new Comment(writer, content, articleId, writeDate);
            comment.setId(rs.getLong("id"));
            return comment;
        };
    }
}
