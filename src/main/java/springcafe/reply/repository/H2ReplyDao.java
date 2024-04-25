package springcafe.reply.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springcafe.article.model.Article;
import springcafe.reply.model.Reply;
import java.util.List;

@Repository
public class H2ReplyDao implements ReplyDao {
    JdbcTemplate jdbcTemplate;

    public H2ReplyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Article article, String content, Reply reply) {
        jdbcTemplate.update(
                "INSERT into REPLY(CONTENT, ARTICLE_ID, WRITER) values (?,?,?)"
                ,content, article.getId(), reply.getWriter()
        );
    }

    @Override
    public List<Reply> findByArticleId(Long articleId) {
        return jdbcTemplate.query(
                "SELECT * FROM REPLY WHERE ARTICLE_ID=? AND IS_DELETED =FALSE", new Object[]{articleId},
                (rs, rowNum) -> new Reply(rs.getLong("id")
                        , rs.getString("CONTENT")
                        , rs.getLong("ARTICLE_ID")
                        , rs.getString("WRITER")
                        , rs.getTimestamp("CREATE_DATE").toLocalDateTime()));
    }

    @Override
    public Reply findByReplyId(Long replyId) {
        return jdbcTemplate.queryForObject(
                "SELECT ID, CONTENT, ARTICLE_ID, WRITER, CREATE_DATE FROM Reply WHERE ID=? AND IS_DELETED =FALSE",
                new Object[]{replyId},
                (rs, rowNum) -> new Reply(
                        rs.getLong("ID"),
                        rs.getString("CONTENT"),
                        rs.getLong("ARTICLE_ID"),
                        rs.getString("WRITER"),
                        rs.getTimestamp("CREATE_DATE").toLocalDateTime()
                )
        );
    }

    @Override
    public void delete(Long replyId) {
     jdbcTemplate.update(
             "UPDATE REPLY SET IS_DELETED=TRUE WHERE ID=?", replyId
     );
    }
}