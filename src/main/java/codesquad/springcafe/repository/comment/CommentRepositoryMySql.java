package codesquad.springcafe.repository.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CommentRepositoryMySql implements CommentRepository {
    private static final Logger log = LoggerFactory.getLogger(CommentRepositoryMySql.class);
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

    @Override
    public Page<Comment> findAllByArticleId(long articleId, PageRequest pageRequest) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(template);
        String findSql = "SELECT COMMENT_ID, ARTICLE_ID, CONTENT, CREATED_BY, CREATED_AT, DELETED "
                + "FROM COMMENT "
                + "WHERE ARTICLE_ID = :articleId and DELETED is false "
                + "ORDER BY CREATED_AT "
                + "LIMIT :offset, :pageSize;";

        String totalSql = "select COUNT(*) as total FROM COMMENT WHERE deleted is false and ARTICLE_ID = ?;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("articleId", articleId);
        parameters.addValue("offset", pageRequest.getOffset());
        parameters.addValue("pageSize", pageRequest.getPageSize());

        List<Comment> comments = namedTemplate.query(findSql, parameters, replyRowMapper());
        Integer total = template.queryForObject(totalSql, Integer.class, articleId);

        return new Page<>(comments, PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), pageRequest.getSort()), total);
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
        String uncheck = "set foreign_key_checks = 0;";
        String truncate = "TRUNCATE TABLE COMMENT;";
        String autoIncrementReset = "ALTER TABLE COMMENT AUTO_INCREMENT = 1;";
        String check = "set foreign_key_checks = 1;";

        template.update(uncheck);
        template.update(truncate);
        template.update(autoIncrementReset);
        template.update(check);
    }
}
