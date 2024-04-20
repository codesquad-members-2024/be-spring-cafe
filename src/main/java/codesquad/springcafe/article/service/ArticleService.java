package codesquad.springcafe.article.service;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.user.domain.UserIdentity;

import java.util.List;

public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(String articleId) throws NoSuchArticleException;

    List<Article> getArticlesAsList();

    void updateArticle(Article article) throws NoSuchArticleException;

    void deleteArticle(String articleId) throws NoSuchArticleException;

    boolean userHasPermission(UserIdentity userId, Article article) throws NoSuchArticleException;
}
