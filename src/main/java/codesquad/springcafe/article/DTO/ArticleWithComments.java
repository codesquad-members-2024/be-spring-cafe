package codesquad.springcafe.article.DTO;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.comment.DTO.Comment;

import java.util.List;

public record ArticleWithComments(Article article, List<Comment> comments) {
}
