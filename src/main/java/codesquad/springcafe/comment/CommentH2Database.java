package codesquad.springcafe.comment;

import codesquad.springcafe.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class CommentH2Database implements CommentDatabase {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentH2Database(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<CommentShowDTO> commentRowMapper = (rs, rowNum) -> {
        CommentShowDTO commentShowDTO = new CommentShowDTO(rs.getLong("commentId"),
                rs.getString("writer"),
                rs.getString("content"),
                rs.getTimestamp("createdTime").toLocalDateTime());
        return commentShowDTO;
    };

    @Override
    public void createComment(CommentCreateDTO commentCreateDTO) {
        String sql = "INSERT INTO MAIN.COMMENTS (articleId, writer, content, createdTime) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, commentCreateDTO.getArticleId(),
                commentCreateDTO.getWriter(),
                commentCreateDTO.getContent(),
                commentCreateDTO.getCreatedTime());
    }

    @Override
    public String getCommentWriter(Long commentId) {
        String sql =  "SELECT writer FROM MAIN.COMMENTS WHERE commentId = ?";
        String result = (String) jdbcTemplate.queryForObject(sql, String.class, commentId);
        System.out.println(result);
        return result;
    }

    @Override
    public List<CommentShowDTO> getCommentList(Long articleId) {
        String sql = "SELECT * FROM MAIN.COMMENTS WHERE articleId = ?";
        return jdbcTemplate.query(sql, commentRowMapper, articleId);
    }

    @Override
    public void deleteComment(Long commentId) {
        String sql = "DELETE FROM MAIN.COMMENTS WHERE commentId = ?";
        jdbcTemplate.update(sql, commentId);
    }
}
