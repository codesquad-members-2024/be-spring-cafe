package codesquad.springcafe.domain.article.DTO;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.domain.comment.DTO.Comment;

import java.util.List;

public record ArticleWithComments(Article article, List<Comment> comments) {
}
