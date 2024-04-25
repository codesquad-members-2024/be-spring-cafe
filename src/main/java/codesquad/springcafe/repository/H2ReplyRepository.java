package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.domain.repository.ReplyRepository;
import codesquad.springcafe.dto.ShowReply;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2ReplyRepository implements ReplyRepository {
    private static final String CONTENTS_KEY = "contents";
    private static final String WRITERID_KEY = "writerId";
    private static final String TIME_KEY = "time";
    private JdbcTemplate jdbcTemplate;
    private RowMapper<ShowReply> showReplyRowMapper = (rs, rowNum) -> {
        ShowReply showReply = new ShowReply(
                rs.getString(WRITERID_KEY),
                rs.getTimestamp(TIME_KEY).toLocalDateTime(),
                rs.getString(CONTENTS_KEY)
        );
        return showReply;
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
    public void delete(Reply reply) {

    }
}