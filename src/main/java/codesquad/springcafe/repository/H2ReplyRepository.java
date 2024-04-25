package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.repository.ReplyRepository;
import codesquad.springcafe.dto.ShowReply;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2ReplyRepository implements ReplyRepository {
    private static final String CONTENTS_KEY = "contents";
    private static final String WRITERID_KEY = "writerId";
    private static final String TIME_KEY = "time";
    private static final String ID_KEY = "id";
    private static final String DELETED_KEY = "deleted";
    private static final String ARTICLE_ID_KEY = "articleId";


    private JdbcTemplate jdbcTemplate;
    private RowMapper<ShowReply> showReplyRowMapper = (rs, rowNum) -> {
        ShowReply showReply = new ShowReply(
                rs.getString(WRITERID_KEY),
                rs.getTimestamp(TIME_KEY).toLocalDateTime(),
                rs.getString(CONTENTS_KEY),
                rs.getBoolean(DELETED_KEY)
        );
        showReply.setId(rs.getLong(ID_KEY));
        return showReply;
    };
    private RowMapper<Reply> replyRowMapper = (rs, rowNum) -> {
        Reply reply = new Reply(
                rs.getString(CONTENTS_KEY),
                rs.getString(WRITERID_KEY),
                rs.getTimestamp(TIME_KEY).toLocalDateTime(),
                rs.getBoolean(DELETED_KEY),
                rs.getLong(ARTICLE_ID_KEY)
        );
        reply.setId(rs.getLong(ID_KEY));
        return reply;
    };

    @Autowired
    public H2ReplyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Reply reply) {
        String INSERT_REPLY = "INSERT INTO REPLY (contents, writerid, deleted, articleid, time) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(INSERT_REPLY, reply.getContents(), reply.getWriterId(), reply.getDeleted(),
                reply.getArticleId(), reply.getTime());
    }

    @Override
    public List<ShowReply> getReplyBy(String articleId) {
        String SELECT_REPLY = "SELECT * FROM REPLY where articleid=?";
        return jdbcTemplate.query(SELECT_REPLY, showReplyRowMapper, articleId);
    }

    @Override
    public void delete(String replyId) {
        String DELETE_REPLY = "UPDATE REPLY SET DELETED = true WHERE id=?";
        jdbcTemplate.update(DELETE_REPLY, replyId);
    }

    @Override
    public Optional<Reply> getReplyById(String replyId) {
        String SELECT_REPLY = "SELECT * FROM REPLY where id=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_REPLY,replyRowMapper,replyId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void edit(String replyId, String contents) {
        String UPDATE_REPLY = "UPDATE REPLY SET contents=? WHERE id=?";
        jdbcTemplate.update(UPDATE_REPLY, contents, replyId);
    }
}