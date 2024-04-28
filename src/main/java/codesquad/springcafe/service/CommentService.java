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

    public List<Comment> getCommentsByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id);
    }
}
