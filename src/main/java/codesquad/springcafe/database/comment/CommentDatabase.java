package codesquad.springcafe.database.comment;

import codesquad.springcafe.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentDatabase {
    Comment add(Comment comment);

    Optional<Comment> findBy(Long id);

    List<Comment> findAll(Long articleId);

    void delete(Long id);

    void clear();
}
