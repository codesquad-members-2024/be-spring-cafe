package codesquad.springcafe.domain.comment.model;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.global.rowMapper.SimpleRowMapper;
import codesquad.springcafe.global.utils.AliasGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class CommentRepositoryH2 implements CommentRepository{
    private final Logger logger = LoggerFactory.getLogger(CommentRepositoryH2.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleRowMapper<Comment> commentRowMapper;

    @Autowired
    public CommentRepositoryH2(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.commentRowMapper = new SimpleRowMapper<>(Comment.class);
    }

    @Override
    public Comment save(Comment comment) {
        final String sql = "INSERT INTO comment(userId, questionId, content) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, comment.getUser().getId());
            ps.setLong(2, comment.getQuestionId());
            ps.setString(3, comment.getContent());
            return ps;
        }, keyHolder);

        Long key = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        comment.setId(key);

        logger.info("Comment saved successfully! | commentId : {} | query : {}", key, sql);

        return comment;
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        final String sql = "select * from comment where id = ? and deleted = false";
        List<Comment> comments = jdbcTemplate.query(sql, commentRowMapper, commentId);

        if (comments.size() > 1) {
            throw new RuntimeException("같은 아이디의 댓글을 두 개 이상 조회하고 있습니다.");
        }

        logger.info("Comment find by Id | commentId: {} | query : {}", commentId, sql);

        return comments.stream().findFirst();
    }

    @Override
    public Collection<Comment> findByQuestionId(Long questionId) {
        final String sql = "select " + AliasGenerator.generateAliases(User.class) + ", " +
                AliasGenerator.generateAliases(Comment.class) +
                " from comment left outer join users on comment.USERID = users.ID " +
                "where comment.questionId = ? and comment.deleted = false";
        logger.info("Find All Comments By QuestionId Join users | questionId : {} | query : {}", questionId, sql);
        return jdbcTemplate.query(sql, commentRowMapper, questionId);
    }

    @Override
    public void softDeleteById(Long commentId, Comment deleteComment) {
        final String sql = "update comment set deleted = ? where id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, deleteComment.getDeleted());
            ps.setLong(2, commentId);
            return ps;
        });

        logger.info("Soft Delete Comment | commentId: {} | query: {}", commentId, sql);
    }

    @Override
    public void update(Long commentId, Comment updateComment) {
        final String sql = "update comment set CONTENT = ?, modified = ? where id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, updateComment.getContent());
            ps.setBoolean(2, updateComment.getModified());
            ps.setLong(3, commentId);
            return ps;
        });

        logger.info("Comment update | commentId : {} | query : {}", commentId, sql);
    }

    @Override
    public void deleteAll() {
        final String sql = "delete from comment";
        logger.info("Delete All Comments | query : {}", sql);
        jdbcTemplate.update(sql);
    }
}
