package codesquad.springcafe.reply.database;

import codesquad.springcafe.reply.Reply;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyH2Database implements ReplyDatabase {

    private final Logger log = LoggerFactory.getLogger(ReplyH2Database.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public ReplyH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("REPLY")
            .usingGeneratedKeyColumns("replyId");
    }

    @Override
    public Reply save(Reply reply) {
        Map<String, Object> parameters = Map.of(
            "author", reply.getAuthor(),
            "contents", reply.getContents(),
            "userId", reply.getUserId(),
            "articleId", reply.getArticleId(),
            "createdTime", reply.getCreatedTime(),
            "deleted", false
        );

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findById(key.longValue());
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        String sql = "SELECT * FROM reply WHERE articleId = ? AND deleted = false";
        return jdbcTemplate.query(sql, new ReplyRowMapper(), articleId);
    }

    @Override
    public void delete(Long replyId) {
        String sql = "UPDATE reply SET deleted = true WHERE replyId = ?";
        jdbcTemplate.update(sql, replyId);
    }

    @Override
    public Reply findById(Long replyId) {
        String sql = "SELECT * FROM reply WHERE replyId = ?";
        return jdbcTemplate.queryForObject(sql, new ReplyRowMapper(), replyId);
    }
}
