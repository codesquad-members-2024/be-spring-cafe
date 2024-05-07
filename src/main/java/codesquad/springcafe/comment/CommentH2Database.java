package codesquad.springcafe.comment;

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
                rs.getTimestamp("lastEditTime").toLocalDateTime(),
                rs.getBoolean("isEdited"));
        return commentShowDTO;
    };

    @Override
    public void createComment(CommentCreateDTO commentCreateDTO) {
        String sql = "INSERT INTO MAIN.COMMENTS (articleId, writer, content, createdTime, lastEditTime) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, commentCreateDTO.getArticleId(),
                commentCreateDTO.getWriter(),
                commentCreateDTO.getContent(),
                commentCreateDTO.getCreatedTime(),
                commentCreateDTO.getCreatedTime()); // 댓글 생성 시의 마지막 수정시간 = 생성시간
    }

    @Override
    public String getCommentWriter(Long commentId) {
        String sql =  "SELECT writer FROM MAIN.COMMENTS WHERE commentId = ?";
        return jdbcTemplate.queryForObject(sql, String.class, commentId);
    }

    @Override
    public List<CommentShowDTO> getCommentList(Long articleId) {
        String sql = "SELECT * FROM MAIN.COMMENTS WHERE articleId = ? AND isDeleted = FALSE";
        return jdbcTemplate.query(sql, commentRowMapper, articleId);
    }

    @Override
    public boolean hasOtherComment(Long articleId, String writer) {
        String sql = "SELECT EXISTS (SELECT 1 FROM MAIN.COMMENTS WHERE articleId = ? AND writer != ? AND isDeleted = FALSE)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, articleId, writer));
    }

    @Override
    public void deleteComment(Long commentId) {
        String sql = "UPDATE MAIN.COMMENTS SET isDeleted = TRUE WHERE commentId = ?";
        jdbcTemplate.update(sql, commentId);
    }

    @Override
    public void editComment(CommentEditDTO comment) {
        String sql = "UPDATE MAIN.COMMENTS SET content = ?, lastEditTime = ?, isEdited = TRUE WHERE commentId = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getEditedTime(), comment.getCommentId());
    }
}
