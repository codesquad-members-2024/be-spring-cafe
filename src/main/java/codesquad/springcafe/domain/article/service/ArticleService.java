package codesquad.springcafe.domain.article.service;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.domain.user.dto.UserIdentity;

import java.util.List;

public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(String articleId) throws NoSuchArticleException;

    List<Article> getArticlesAsList();

    void updateArticle(Article article) throws NoSuchArticleException;

    void deleteArticle(String articleId) throws NoSuchArticleException;

    boolean userHasPermission(UserIdentity userId, Article article) throws NoSuchArticleException;
}
