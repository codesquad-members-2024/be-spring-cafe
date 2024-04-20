package codesquad.springcafe.domain.comment.service;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.comment.dto.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getComments(Article article);
}
