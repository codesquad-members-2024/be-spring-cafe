package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.exceptions.NoSuchCommentException;

import java.util.List;

public interface CommentRepository {

    void add(Comment comment);

    List<Comment> getComments(String writtenArticleId);

    Comment get(String id) throws NoSuchCommentException;

    void update(String commentId, String contents) throws NoSuchCommentException;

    void addLike(String commentId) throws NoSuchCommentException;

    void delete(String commentId) throws NoSuchCommentException;
}
