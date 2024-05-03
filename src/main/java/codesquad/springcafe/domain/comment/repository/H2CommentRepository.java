package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.repository.util.CommentRowMapper;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.exceptions.NoSuchCommentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2CommentRepository implements CommentRepository{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Comment comment) {
        String sql = "insert into COMMENTS (IDENTIFIER, WRITER, WRITTENARTICLE, CREATETIME, contents, LIKECOUNT) values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                comment.getIdentifier(), comment.getWriter(), comment.getWrittenArticle(), comment.getRoughCreateTime(), comment.getContents(), comment.getLikeCount());
        logger.debug("[{}] 아티클에 [{}] 댓글 추가 완료", comment.getWrittenArticle(), comment.getIdentifier());
    }

    @Override
    public List<Comment> getComments(String writtenArticleId) {
        String sql = "select * from COMMENTS where writtenArticle = ?";
        return jdbcTemplate.query(sql, new CommentRowMapper(), writtenArticleId);
    }

    @Override
    public Comment get(String id) throws NoSuchCommentException {
        String sql = "select * from COMMENTS where identifier = ?";
        List<Comment> comments = jdbcTemplate.query(sql, new CommentRowMapper(), id);
        if (comments.isEmpty()) throw new NoSuchCommentException();

        logger.debug("[{}] 댓글 검색 완료", id);
        return comments.getFirst();
    }

    @Override
    public void update(String commentId, String contents) throws NoSuchCommentException {
        String sql = "UPDATE COMMENTS SET contents = ? WHERE identifier = ?";

        int update = jdbcTemplate.update(sql, contents, commentId);
        if (update == 0) throw new NoSuchCommentException();

        logger.debug("[{}] 댓글 업데이트 완료", commentId);
    }

    @Override
    public void addLike(String commentId) throws NoSuchCommentException {
        String sql = "UPDATE COMMENTS SET likeCount = likeCount + 1 where identifier = ?";
        int update = jdbcTemplate.update(sql, commentId);
        if (update == 0) throw new NoSuchCommentException();
    }

    @Override
    public void delete(String commentId) throws NoSuchCommentException {
        String sql = "delete from COMMENTS where identifier = ?";

        int update = jdbcTemplate.update(sql, commentId);
        if (update == 0) throw new NoSuchArticleException();
        logger.debug("[{}] 댓글 삭제 완료", commentId);
    }

}
