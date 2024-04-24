package codesquad.springcafe.repository.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByArticleId(long articleId);

    Page<Comment> findAllByArticleId(long articleId, PageRequest pageRequest);

    void update(CommentUpdateForm updateParam);

    void delete(long id);

    void softDelete(long id);

    void bulkSoftDelete(List<Long> commentIds);

    void restore(long id);

    void clear();
}
