package codesquad.springcafe.repository;

import codesquad.springcafe.exception.ArticleNotFountException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
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
public class ReplyJdbcRepository implements ReplyRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReplyJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reply save(Reply reply) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("reply").usingGeneratedKeyColumns("reply_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("article_id", reply.getArticleId());
        parameters.put("author", reply.getAuthor());
        parameters.put("content", reply.getContent());
        parameters.put("createdAt", reply.getCreatedAt());
        parameters.put("deleted", reply.isDeleted());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        reply.setReplyId((key.longValue()));

        return reply;
    }

    @Override
    public Optional<Reply> findById(Long replyId) {
        String sql = "SELECT reply_id, article_id, author, content, createdAt, deleted FROM reply WHERE reply_id = ?";
        try {
            Reply reply = jdbcTemplate.queryForObject(sql, replyRowMapper(), replyId);
            return Optional.ofNullable(reply);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFountException();
        }
    }

    @Override
    public List<Reply> findRepliesByArticleId(Long articleId) {
        String sql = "SELECT reply_id, article_id, author, content, createdAt, deleted FROM reply";
        List<Reply> replies = jdbcTemplate.query(sql, replyRowMapper());

        return replies.stream()
                .filter(reply -> reply.getArticleId().equals(articleId))
                .toList();
    }

    @Override
    public void delete(Long replyId) {
        String sql = "DELETE FROM reply WHERE reply_id = ?";
        jdbcTemplate.update(sql, replyId);
    }

    private RowMapper<Reply> replyRowMapper() {
        return (rs, rowNum) -> {
            long replyId = rs.getLong("reply_id");
            long articleId = rs.getLong("article_id");
            String author = rs.getString("author");
            String content = rs.getString("content");
            LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
            boolean deleted = rs.getBoolean("deleted");

            return new Reply(replyId, articleId, author, content, createdAt, deleted);
        };
    }
}
