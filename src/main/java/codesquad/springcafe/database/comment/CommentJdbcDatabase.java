package codesquad.springcafe.database.comment;

import codesquad.springcafe.model.Comment;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CommentJdbcDatabase implements CommentDatabase {
    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment add(Comment comment) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("comments").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("writer", comment.getWriter());
        params.put("content", comment.getContent());
        params.put("write_date", comment.getWriteDate());
        params.put("article_id", comment.getArticleId());
        params.put("is_deleted", comment.isDeleted());

        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        comment.setId(key.longValue());

        return comment;
    }

    @Override
    public Optional<Comment> findBy(Long id) {
        String sql = "SELECT id, writer, content, write_date, article_id, is_deleted FROM comments WHERE id = ? and is_deleted=false";
        return jdbcTemplate.query(sql, commentRowMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public List<Comment> findAll(Long articleId) {
        String sql = "SELECT id, writer, content, write_date, article_id, is_deleted FROM comments WHERE article_id = ? and is_deleted=false";
        return jdbcTemplate.query(sql, commentRowMapper(), articleId);
    }

    @Override
    public List<Comment> findPageComments(Long articleId, Long offset, int pagePerComments) {
        String sql = "SELECT id, writer, content, write_date, article_id, is_deleted FROM comments WHERE article_id = ? and is_deleted=false LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, commentRowMapper(), articleId, pagePerComments, offset);
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET writer = ?, content = ?, write_date = ?, article_id = ?, is_deleted = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getWriter(), comment.getContent(), comment.getWriteDate(),
                comment.getArticleId(), comment.isDeleted(), comment.getId());
    }

    @Override
    public void softDelete(Long id) {
        String sql = "UPDATE comments SET is_deleted = true WHERE id =?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Long count(Long articleId) {
        String sql = "SELECT COUNT(*) FROM comments WHERE article_id = ? AND is_deleted = false";
        return jdbcTemplate.queryForObject(sql, Long.class, articleId);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM comments";
        jdbcTemplate.update(sql);
    }

    @Override
    public String findWriter(Long id) {
        String sql = "SELECT writer FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    @Override
    public void softDeleteComments(Long articleId) {
        String sql = "UPDATE comments SET is_deleted = true WHERE article_id = ?";
        jdbcTemplate.update(sql, articleId);
    }

    @Override
    public List<String> findWriters(Long articleId) {
        String sql = "SELECT writer FROM comments WHERE article_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("writer"), articleId);
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> {
            String writer = rs.getString("writer");
            String content = rs.getString("content");
            long articleId = rs.getLong("article_id");
            LocalDateTime writeDate = rs.getTimestamp("write_date").toLocalDateTime();

            Comment comment = new Comment(writer, content, articleId, writeDate);
            comment.setId(rs.getLong("id"));
            return comment;
        };
    }
}
