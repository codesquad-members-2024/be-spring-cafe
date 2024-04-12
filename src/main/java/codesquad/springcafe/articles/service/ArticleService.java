package codesquad.springcafe.articles.service;

import model.Article;
import model.ArticleData;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleService {
    void createArticle(ArticleData articleData);

    ArrayList<Article> getAllArticles();

    Article findArticleById(int articleId);
}
