package codesquad.springcafe.repository.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CommentRepositoryMySql implements CommentRepository {
    private final JdbcTemplate template;

    @Autowired
    public CommentRepositoryMySql(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Comment save(Comment comment) {
        String sql = "INSERT INTO COMMENT (ARTICLE_ID, CONTENT, CREATED_BY, CREATED_AT) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, comment.getArticleId());
            ps.setString(2, comment.getContent());
            ps.setString(3, comment.getCreatedBy());
            ps.setTimestamp(4, Timestamp.valueOf(comment.getCreatedAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("pk를 생성하지 못했습니다");
        }

        comment.setId(key.longValue());
        return comment;
    }

    @Override
    public Optional<Comment> findById(long id) {
        String sql = "SELECT COMMENT_ID, ARTICLE_ID, CONTENT, CREATED_BY, CREATED_AT, DELETED FROM COMMENT WHERE COMMENT_ID = ? and DELETED is false";
        try {
            return Optional.ofNullable(template.queryForObject(sql, replyRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findAllByArticleId(long articleId) {
        String sql = "SELECT COMMENT_ID, ARTICLE_ID, CONTENT, CREATED_BY, CREATED_AT, DELETED FROM COMMENT WHERE ARTICLE_ID = ? and DELETED is false ORDER BY CREATED_AT";
        return template.query(sql, replyRowMapper(), articleId);
    }

    private RowMapper<Comment> replyRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("comment_id"));
            comment.setArticleId(rs.getLong("article_id"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedBy(rs.getString("created_by"));
            comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            comment.setDeleted(rs.getBoolean("deleted"));
            return comment;
        };
    }

    @Override
    public void update(CommentUpdateForm updateParam) {
        String sql = "update COMMENT set CONTENT = ? where COMMENT_ID = ?";
        template.update(sql, updateParam.getContent(), updateParam.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "delete from COMMENT where COMMENT_ID = ?";
        template.update(sql, id);
    }

    @Override
    public void softDelete(long id) {
        String sql = "update COMMENT set DELETED = ? where COMMENT_ID = ?";
        template.update(sql, true, id);
    }

    @Override
    public void bulkSoftDelete(List<Long> commentIds) {
        String sql = "update COMMENT set DELETED = ? where COMMENT_ID = ?";
        template.batchUpdate(sql, commentIds, commentIds.size(), (ps, commentId) -> {
            ps.setBoolean(1, true);
            ps.setLong(2, commentId);
        });
    }

    @Override
    public void restore(long id) {
        String sql = "update COMMENT set DELETED = ? where COMMENT_ID = ?";
        template.update(sql, false, id);
    }

    @Override
    public void clear() {
        String sql = "set foreign_key_checks = 0;"
                + "alter table comment drop foreign key fk_comment_created_by;"
                + "alter table comment drop foreign key fk_comment_article_id;"
                + "TRUNCATE TABLE COMMENT; ALTER TABLE COMMENT AUTO_INCREMENT = 1;"
                + "set foreign_key_checks = 1;";

        template.update(sql);
    }
}
