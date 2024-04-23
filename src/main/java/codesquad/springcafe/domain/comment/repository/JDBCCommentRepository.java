package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.DTO.CommentPostReq;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Primary
@Repository
public class JDBCCommentRepository implements CommentRepository {

    private final String ADD_COMMENT = "INSERT INTO comment (ARTICLEID, CREATEDAT, AUTHORID, CONTENT) VALUES (?, ?, ?, ?);";
    private final String FIND_BY_ARTICLE_ID = "SELECT * FROM comment WHERE ArticleId = ? AND STATUS = 'OPEN' ORDER BY createdAt";
    private final String FIND_BY_USER_ID = "SELECT * FROM comment WHERE AuthorId = ? AND STATUS = 'OPEN' ORDER BY createdAt DESC;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment add(CommentPostReq commentPostReq, SimpleUserInfo simpleUserInfo) throws IllegalArgumentException {
        Timestamp createdDateTime = Timestamp.valueOf(LocalDateTime.now());
        Object[] args = new Object[]{
                commentPostReq.articleId(),
                createdDateTime,
                simpleUserInfo.id(),
                commentPostReq.content()
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(ADD_COMMENT, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().intValue());
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
    public List<Comment> findByArticleId(int articleId, int page) {
        String paging_query = " LIMIT ? OFFSET ?";
        int COMMENTS_PER_PAGE = 15;
        int START_INDEX = COMMENTS_PER_PAGE * (page - 1);

        Object[] args = new Object[]{articleId, COMMENTS_PER_PAGE, START_INDEX};
        int[] pramTypes = new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER};
        return jdbcTemplate.query(FIND_BY_ARTICLE_ID + paging_query, args, pramTypes, commentRowMapper());
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
        } catch (IndexOutOfBoundsException commentNotFound) {
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
