package codesquad.springcafe.service.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import java.util.List;

public interface CommentService {
    Comment publish(Comment comment);

    Comment findComment(long commentId);

    List<Comment> findAllComment(long articleId);

    void editComment(String loginId, CommentUpdateForm updateParam);

    void unpublish(long id);

    void validateAuthor(String loginId, String author);
}
