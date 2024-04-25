package codesquad.springcafe.comment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentH2Dao implements CommentDao{
    private final JdbcTemplate jdbcTemplate;

    public CommentH2Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Comment comment) {
        final String sql = "INSERT INTO COMMENT (articleId, writer, contents, createdAt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, comment.getArticleId(), comment.getWriter(), comment.getContents(), comment.getCreatedAt());
    }
}
