package codesquad.springcafe.service;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.UpdatedArticle;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    void addArticle(Article article);

    Optional<Article> findArticleById(long id);

    void modifyArticle(long id, UpdatedArticle article);

    void deleteArticle(long id);

    List<Article> findAllArticle();

    void increaseViewCount(long id);
}
