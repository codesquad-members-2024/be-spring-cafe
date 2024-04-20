package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.repository.util.CommentRowMapper;
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

}
