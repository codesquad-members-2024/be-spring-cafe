package codesquad.springcafe.reply.database;

import codesquad.springcafe.reply.Reply;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyH2Database implements ReplyDatabase {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReplyH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Reply reply) {
        String sql = "INSERT INTO reply (author, contents, userId, articleId, createdTime) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reply.getAuthor(), reply.getContents(), reply.getUserId(),
            reply.getArticleId(), reply.getCreatedTime());
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        String sql = "SELECT * FROM reply WHERE articleId = ?";
        return jdbcTemplate.query(sql, new ReplyRowMapper(), articleId);
    }

    @Override
    public void delete(Long replyId) {
        String sql = "DELETE FROM reply WHERE replyId = ?";
        jdbcTemplate.update(sql, replyId);

    }

    @Override
    public Reply findById(Long replyId) {
        String sql = "SELECT * FROM reply WHERE replyId = ?";
        return jdbcTemplate.queryForObject(sql, new ReplyRowMapper(), replyId);
    }
}
