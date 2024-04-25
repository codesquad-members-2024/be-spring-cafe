package codesquad.springcafe.repository.reply;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcReplyRepository implements ReplyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Reply reply) {
        String sql = "INSERT INTO `reply` (articleId, index, writer, timestamp, content) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            reply.getArticleId(), reply.getIndex(), reply.getWriter(), Timestamp.valueOf(reply.getTimestamp()), reply.getContent());
    }

    @Override
    public List<Reply> getAllByArticleId(Long articleId) {
        String sql = "SELECT * FROM `reply` WHERE articleId = ?";
        return jdbcTemplate.query(sql, new Object[]{articleId}, (resultSet, rowNum) -> {
            Reply reply = new Reply(
                resultSet.getLong("articleId"),
                resultSet.getLong("index"),
                resultSet.getString("writer"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("content")
            );
            return reply;
        });
    }

    @Override
    public Optional<Reply> getByArticleIdAndIndex(Long articleId, Long index) {
        String sql = "SELECT * FROM `reply` WHERE articleId = ? AND index = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{articleId, index}, (resultSet, rowNum) -> {
            Reply reply = new Reply(
                resultSet.getLong("articleId"),
                resultSet.getLong("index"),
                resultSet.getString("writer"),
                resultSet.getTimestamp("timestamp").toLocalDateTime(),
                resultSet.getString("content")
            );
            return Optional.ofNullable(reply);
        });
    }
}