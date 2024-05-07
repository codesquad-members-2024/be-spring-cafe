package codesquad.springcafe.database.comment;

import codesquad.springcafe.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentDatabase {
    Comment add(Comment comment);

    Optional<Comment> findBy(Long id);

    List<Comment> findAll(Long articleId);

    List<Comment> findPageComments(Long articleId, Long offset, int commentsPerPage);

    void update(Comment comment);
//    void deleteArticle(Long id);

    void softDelete(Long id);

    Long count(Long ArticleId);

    void clear();

    void softDeleteComments(Long articleId);

    List<String> findWriters(Long articleId);

    String findWriter(Long id);
}
