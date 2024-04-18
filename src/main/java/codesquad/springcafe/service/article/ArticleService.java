package codesquad.springcafe.service.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article publish(Article article);

    Optional<Article> findArticle(long id);

    List<Article> findAllArticle();

    void validateExists(long id);

    void validateAuthor(String loginId, String author);

    void editArticle(String loginId, UpdateArticle updateParam);

    void unpublish(String loginId, long id);
}
