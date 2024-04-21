package codesquad.springcafe.repository.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByArticleId(long articleId);

    void update(CommentUpdateForm updateParam);

    void delete(long id);

    void softDelete(long id);

    void restore(long id);

    void clear();
}
