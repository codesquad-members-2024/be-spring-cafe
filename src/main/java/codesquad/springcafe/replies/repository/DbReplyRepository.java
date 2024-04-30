package codesquad.springcafe.replies.repository;

import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.articles.repository.DbArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DbReplyRepository implements ReplyRepository {
    private static final Logger logger = LoggerFactory.getLogger(DbArticleRepository.class);

    private static final String ARTICLEID = "ARTICLEID";
    private static final String USERID = "USERID";
    private static final String COMMENT = "COMMENT";
    private static final String REPLYID = "REPLYID";
    private static final String CREATIONDATE = "CREATIONDATE";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbReplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reply createReply(Reply reply) {
        // SQL 쿼리 수정: replyId를 자동 생성하도록 변경
        String sql = "INSERT INTO REPLIES (ARTICLEID, USERID, COMMENT, CREATIONDATE, DELETED) VALUES (?, ?, ?, ?, false)";

        // KeyHolder를 사용하여 생성된 replyId를 저장할 변수를 만듭니다.
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // jdbcTemplate의 update 메서드에 KeyHolder를 전달하여 생성된 키를 얻습니다.
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"replyId"});
            ps.setLong(1, reply.getArticleId());
            ps.setString(2, reply.getUserId());
            ps.setString(3, reply.getComment());
            ps.setString(4, reply.getCreationDate());
            return ps;
        }, keyHolder);

        // 생성된 replyId를 가져와서 Reply 객체에 설정합니다.
        long generatedId = keyHolder.getKey().longValue();
        reply.setReplyId(generatedId);

        logger.debug("Reply Comment : '{}' with replyId: '{}' Created At H2 Database", reply.getComment(), generatedId);

        // 수정된 Reply 객체를 반환합니다.
        return reply;
    }

    @Override
    public Optional<ArrayList<Reply>> getReplies(long articleId) {
        String sql = "SELECT REPLYID, ARTICLEID, USERID, COMMENT, CREATIONDATE FROM REPLIES WHERE ARTICLEID = ? AND DELETED = FALSE";
        Object[] params = new Object[]{articleId};

        ArrayList<Reply> replies = (ArrayList<Reply>) jdbcTemplate.query(sql, params, new DbReplyRepository.ReplyRowMapper());
        return Optional.of(replies);
    }

    @Override
    public boolean deleteReply(long replyId) {
        String sql = "UPDATE REPLIES SET DELETED = TRUE WHERE REPLYID = ?";
        int updatedRow = jdbcTemplate.update(sql, replyId);
        logger.debug("Reply ID : {} Deleted", replyId);
        return updatedRow > 0;
    }

    @Override
    public Optional<Reply> findReplyById(long replyId) {
        String sql = "SELECT REPLYID, ARTICLEID, USERID, COMMENT, CREATIONDATE FROM REPLIES WHERE REPLYID = ? AND DELETED = FALSE";
        Object[] params = new Object[]{replyId};

        List<Reply> replies = jdbcTemplate.query(sql, params, new DbReplyRepository.ReplyRowMapper());
        return Optional.of(replies.get(0));
    }


    private static class ReplyRowMapper implements RowMapper<Reply> {
        @Override
        public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
            long replyId = rs.getLong(REPLYID);
            long articleId = rs.getLong(ARTICLEID);
            String userId = rs.getString(USERID);
            String comment = rs.getString(COMMENT);
            LocalDateTime creationDate = rs.getTimestamp(CREATIONDATE).toLocalDateTime();

            Reply reply = new Reply(articleId, userId, comment);

            reply.setReplyId(replyId);
            reply.setCreationDate(creationDate);

            return reply;
        }
    }

}
