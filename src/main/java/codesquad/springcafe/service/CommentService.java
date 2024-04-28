package codesquad.springcafe.service;

import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.comment.JdbcCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final JdbcCommentRepository commentRepository;

    public CommentService(JdbcCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }

    public List<Comment> getCommentsByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id);
    }

    public boolean isDeletableComments(Long articleId, Long userId) {
        return !commentRepository.findAllByArticleId(articleId).stream()
                .anyMatch(c -> !c.getUserId().equals(userId));
    }
}
