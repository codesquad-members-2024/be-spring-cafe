package codesquad.springcafe.articles.repository;

import model.Article;
import model.ArticleData;

import java.util.ArrayList;

public interface ArticleRepository {
    void createArticle(ArticleData articleData);

    ArrayList<Article> getAllArticles();

    Article findArticleById(int articleId);
}
