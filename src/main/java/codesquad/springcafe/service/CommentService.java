package codesquad.springcafe.service;

import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.domain.comment.JdbcCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final JdbcCommentRepository commentRepository;

    public CommentService(JdbcCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
