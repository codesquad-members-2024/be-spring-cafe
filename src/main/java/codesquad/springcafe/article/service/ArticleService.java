package codesquad.springcafe.article.service;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;

public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(String articleId) throws NoSuchArticleException;
}
