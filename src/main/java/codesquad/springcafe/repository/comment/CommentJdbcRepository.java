package codesquad.springcafe.repository.comment;

import codesquad.springcafe.exception.db.CommentNotFoundException;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.UpdatedComment;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CommentJdbcRepository implements CommentRepository {
    private static final Logger logger = LoggerFactory.getLogger(CommentJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment addArticle(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comment").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("article_id", comment.getArticleId());
        parameters.put("user_id", comment.getUserId());
        parameters.put("content", comment.getContent());
        parameters.put("creation_time", Timestamp.valueOf(comment.getCreationTime()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        comment.setId(key.longValue());

        logger.debug(comment + " JdbcDB 저장 완료");
        return comment;
    }

    @Override
    public Optional<Comment> findCommentById(long id) throws CommentNotFoundException {
        String sql = "SELECT id, article_id, user_id, content, is_modified, is_deleted, creation_time, modified_time FROM comment WHERE id = ?";
        Long[] params = new Long[]{id};
        int[] paramTypes = new int[]{Types.BIGINT};

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, paramTypes, commentRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException(id);
        }
    }

    @Override
    public List<Comment> findCommentsByAid(long articleId) {
        String sql = "SELECT id, article_id, user_id, content, is_modified, is_deleted, creation_time, modified_time FROM comment WHERE article_id = ?";
        Long[] params = new Long[]{articleId};
        int[] paramTypes = new int[]{Types.BIGINT};

        try {
            return jdbcTemplate.query(sql, params, paramTypes, commentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Comment> findCommentsByUserId(String userId) {
        String sql = "SELECT id, article_id, user_id, content, is_modified, is_deleted, creation_time, modified_time FROM comment WHERE userId = ?";
        String[] params = new String[]{userId};
        int[] paramTypes = new int[]{Types.VARCHAR};

        try {
            return jdbcTemplate.query(sql, params, paramTypes, commentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public long modifyComment(long id, UpdatedComment comment) {
        return 0; // TODO
    }

    @Override
    public long deleteComment(long id) {
        return 0; // TODO
    }

    private RowMapper<Comment> commentRowMapper() {
        return (rs, rowNum) -> new Comment(
                rs.getLong("id"),
                rs.getLong("article_id"),
                rs.getString("user_id"),
                rs.getString("content"),
                rs.getBoolean("is_modified"),
                rs.getBoolean("is_deleted"),
                rs.getTimestamp("creation_time").toLocalDateTime(),
                rs.getTimestamp("modified_time") != null ? rs.getTimestamp("modified_time").toLocalDateTime() : null
        );
    }
}
