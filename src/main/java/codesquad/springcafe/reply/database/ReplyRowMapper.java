package codesquad.springcafe.reply.database;

import codesquad.springcafe.reply.Reply;
import codesquad.springcafe.reply.ReplyBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ReplyRowMapper implements RowMapper<Reply> {

    @Override
    public Reply mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ReplyBuilder()
            .replyId(resultSet.getLong("replyId"))
            .author(resultSet.getString("author"))
            .contents(resultSet.getString("contents"))
            .userId(resultSet.getString("userId"))
            .articleId(resultSet.getLong("articleId"))
            .createdTime(resultSet.getTimestamp("createdTime"))
            .deleted(resultSet.getBoolean("deleted"))
            .build();
    }
}
