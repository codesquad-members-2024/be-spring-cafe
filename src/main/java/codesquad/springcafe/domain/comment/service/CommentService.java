package codesquad.springcafe.domain.comment.service;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.dto.DeleteComment;
import codesquad.springcafe.domain.comment.dto.UpdateComment;
import codesquad.springcafe.domain.user.dto.UserIdentity;
import codesquad.springcafe.exceptions.NoSuchCommentException;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getComments(Article article);

    Comment getCommentById(String identifier) throws NoSuchCommentException;

    String updateComment(UpdateComment comment) throws NoSuchCommentException;

    void deleteComment(DeleteComment comment) throws NoSuchCommentException;

    void addLike(String commentId) throws NoSuchCommentException;

    boolean userHasPermission(UserIdentity userIdentity, String commentId) throws NoSuchCommentException;
}
