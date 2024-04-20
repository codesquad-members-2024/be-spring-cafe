package codesquad.springcafe.domain.comment.repository;

import codesquad.springcafe.domain.comment.dto.Comment;

import java.util.List;

public interface CommentRepository {

    void add(Comment comment);

    List<Comment> getComments(String writtenArticleId);
}
