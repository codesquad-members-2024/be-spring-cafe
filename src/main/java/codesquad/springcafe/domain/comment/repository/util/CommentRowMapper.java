package codesquad.springcafe.domain.comment.repository.util;

import codesquad.springcafe.domain.comment.dto.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getString("identifier"),
                rs.getString("writer"),
                rs.getString("writtenArticle"),
                rs.getTimestamp("createTime"),
                rs.getString("contents"),
                rs.getInt("likeCount")
        );
    }
}
