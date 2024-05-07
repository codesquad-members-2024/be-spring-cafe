package codesquad.springcafe.repository.reply;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.error.exception.ReplyNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcReplyRepository implements ReplyRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createReply(Reply reply) {
        String SQL = "INSERT INTO reply (article_id, writer, content, deleted, createdDate) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, reply.getArticleId(), reply.getWriter(), reply.getContent(), reply.isDeleted(),
                reply.getCreatedDate());
    }

    @Override
    public void deleteReply(long replyId) {
        String SQL = "UPDATE reply SET deleted = true WHERE reply_id = ?";
        jdbcTemplate.update(SQL, replyId);
    }

    @Override
    public List<Reply> findRepliesByArticleId(long articleId) {
        String SQL = "SELECT * FROM reply WHERE article_id = ? AND deleted = false";
        return jdbcTemplate.query(SQL, rowMapper(), articleId);
    }

    @Override
    public Optional<Reply> findByReplyId(long replyId) {
        String SQL = "SELECT * FROM reply WHERE reply_id = ?";
        try {
            Reply reply = jdbcTemplate.queryForObject(SQL, rowMapper(), replyId);
            return Optional.ofNullable(reply);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Reply> rowMapper() {
        return (rs, rowNum) -> {
            long replyId = rs.getLong("reply_id");
            long articleId = rs.getLong("article_id");
            String writer = rs.getString("writer");
            String content = rs.getString("content");
            boolean deleted = rs.getBoolean("deleted");
            LocalDateTime createdDate = rs.getTimestamp("createdDate").toLocalDateTime();
            return new Reply(replyId, articleId, writer, content, deleted, createdDate);
        };
    }
}
