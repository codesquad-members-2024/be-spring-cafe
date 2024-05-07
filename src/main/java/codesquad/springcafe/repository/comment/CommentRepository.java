package codesquad.springcafe.repository.comment;

import codesquad.springcafe.exception.db.CommentNotFoundException;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.UpdatedComment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment addArticle(Comment comment);

    Optional<Comment> findCommentById(long id) throws CommentNotFoundException;

    List<Comment> findCommentsByAid(long articleId);

    List<Comment> findCommentsByUserId(String userId);

    void modifyComment(long id, UpdatedComment comment) throws CommentNotFoundException;

    void deleteComment(long commentId);

    void deleteArticlesComment(long articleId);
}
