package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;

import java.util.List;

public interface ArticleRepository {

    void add(Article article);

    List<Article> getArticles();

    Article getArticle(String identifier) throws NoSuchArticleException;
}
