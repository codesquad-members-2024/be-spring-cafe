package codesquad.springcafe.service.impl;

import codesquad.springcafe.exception.db.CommentNotFoundException;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.UpdatedComment;
import codesquad.springcafe.repository.comment.CommentRepository;
import codesquad.springcafe.service.CommentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CommentManagementService implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleManagementService.class);

    private final CommentRepository commentRepository;

    @Autowired
    public CommentManagementService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.addArticle(comment);
        logger.info("[댓글 생성 완료] - " + comment);
    }

    @Override
    public Comment findCommentsById(long id) {
        Optional<Comment> optComment = commentRepository.findCommentById(id);
        return optComment.orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public List<Comment> findCommentsByAid(long articleId) {
        return commentRepository.findCommentsByAid(articleId);
    }

    @Override
    public List<Comment> findCommentsByUserId(String userId) {
        return commentRepository.findCommentsByUserId(userId);
    }

    @Override
    public void updateComment(long id, UpdatedComment comment) {
        commentRepository.modifyComment(id, comment);
        logger.info("[{} 댓글 수정 성공]", id);
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.deleteComment(commentId);
        logger.info("[{} 댓글 삭제 성공]", commentId);
    }

    @Override
    public void deleteArticlesComment(long articleId) {
        commentRepository.deleteArticlesComment(articleId);
        logger.info("[{} 게시글의 댓글 삭제 성공]", articleId);
    }
}
