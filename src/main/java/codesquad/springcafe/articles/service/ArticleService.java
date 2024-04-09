package codesquad.springcafe.articles.service;

import model.Article;
import model.ArticleData;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleService {
    void createArticle(ArticleData articleData);

    ArrayList<Article> getAllArticles();

    Optional<Article> findArticleById(int articleId);
}
