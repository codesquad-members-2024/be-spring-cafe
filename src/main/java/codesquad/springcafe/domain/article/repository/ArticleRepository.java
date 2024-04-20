package codesquad.springcafe.domain.article.repository;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;

import java.util.List;

public interface ArticleRepository {

    void add(Article article);

    List<Article> getArticles();

    Article get(String identifier) throws NoSuchArticleException;

    void update(Article article) throws NoSuchArticleException;

    void delete(String identifier) throws NoSuchArticleException;
}
