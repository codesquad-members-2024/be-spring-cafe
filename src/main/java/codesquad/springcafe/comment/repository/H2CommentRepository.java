package codesquad.springcafe.comment.repository;

import codesquad.springcafe.comment.DTO.Comment;
import codesquad.springcafe.comment.DTO.CommentPostReq;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class H2CommentRepository implements CommentRepository {

    private final String ADD_COMMENT = "INSERT INTO comment (ARTICLEID, CREATEDAT, AUTHORID, CONTENT) VALUES (?, ?, ?, ?);";
    private final String FIND_BY_ARTICLE_ID = "SELECT * FROM comment WHERE ArticleId = ? AND STATUS = 'OPEN' ORDER BY createdAt DESC;";
    private final String FIND_BY_USER_ID = "SELECT * FROM comment WHERE AuthorId = ? AND STATUS = 'OPEN' ORDER BY createdAt DESC;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Timestamp createdDateTime = Timestamp.valueOf(LocalDateTime.now());
        Object[] args = new Object[]{
                commentPostReq.articleId(),
                createdDateTime,
                simpleUserInfo.id(),
                commentPostReq.content()
        };

        jdbcTemplate.update(ADD_COMMENT, args);
    }

    @Override
    public void modify(int id, CommentPostReq commentPostReq) {
        String sql = "UPDATE COMMENT SET CONTENT = ?,  CREATEDAT = ?  WHERE ID = ?;";
        Object[] args = new Object[]{commentPostReq.content(), Timestamp.valueOf(LocalDateTime.now()), id};
        jdbcTemplate.update(sql, args);
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE COMMENT SET STATUS = 'CLOSE' WHERE ID = ? ;";
        Object[] args = new Object[]{id};

        jdbcTemplate.update(sql, args);
    }

    @Override
    public List<Comment> findByArticleId(int articleId) {
        Object[] args = new Object[]{articleId};
        int[] pramTypes = new int[]{Types.VARCHAR};
        return jdbcTemplate.query(FIND_BY_ARTICLE_ID, args, pramTypes, commentRowMapper());
    }

    @Override
    public List<Comment> findByUserId(String userId) {
        Object[] args = new Object[]{userId};
        int[] pramTypes = new int[]{Types.VARCHAR};
        return jdbcTemplate.query(FIND_BY_USER_ID, args, pramTypes, commentRowMapper());
    }

    @Override
    public Comment findById(int id) {
        String sql = "SELECT * FROM COMMENT WHERE ID = ?";
        Object[] args = new Object[]{id};
        int[] pramTypes = new int[]{Types.VARCHAR};
        try {
            return jdbcTemplate.query(sql, args, pramTypes, commentRowMapper()).get(0);
        }catch (IndexOutOfBoundsException commentNotFound){
            return null;
        }
    }

    private RowMapper<Comment> commentRowMapper() {
        return (resultSet, rowNum) -> new Comment(
                resultSet.getInt("id"),
                resultSet.getInt("articleId"),
                resultSet.getString("content"),
                resultSet.getTimestamp("createdAt"),
                getUserName(resultSet.getString("authorId")),
                resultSet.getString("authorId")
        );
    }

    private final String GET_USER_NAME = "SELECT NAME FROM USERS WHERE USERID = ?";

    private String getUserName(String id) {
        try {
            Object[] args = new Object[]{id};
            int[] paramTypes = new int[]{Types.VARCHAR};
            return jdbcTemplate.queryForObject(GET_USER_NAME, args, paramTypes, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
