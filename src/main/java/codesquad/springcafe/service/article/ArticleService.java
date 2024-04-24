package codesquad.springcafe.service.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.util.List;

public interface ArticleService {
    Article publish(Article article);

    Article findArticle(long id);

    List<Article> findAllArticle();

    Page<Article> findAllArticle(PageRequest pageRequest);

    void validateAuthor(String loginId, String author);

    void editArticle(String loginId, UpdateArticle updateParam);

    void unpublish(long id);
}
