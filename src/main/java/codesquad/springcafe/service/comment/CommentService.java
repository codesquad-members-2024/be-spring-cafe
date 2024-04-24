package codesquad.springcafe.service.comment;

import codesquad.springcafe.controller.comment.CommentUpdateForm;
import codesquad.springcafe.domain.comment.Comment;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.util.List;

public interface CommentService {
    Comment publish(Comment comment);

    Comment findComment(long commentId);

    List<Comment> findAllComment(long articleId);

    Page<Comment> findAllComment(long articleId, PageRequest page);

    void editComment(String loginId, CommentUpdateForm updateParam);

    void unpublish(long id);

    void validateAuthor(String loginId, String author);
}
