package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Article;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleRepository {
    void createArticle(Article article);

    Optional<ArrayList<Article>> getAllArticles();

    Optional<Article> findArticleById(long articleId);

    void incrementPageView(long articleId);
}
