package codesquad.springcafe.articles.service;

import model.Article;
import model.ArticleVO;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleService {
    void createArticle(ArticleVO articleVO);

    ArrayList<Article> getAllArticles();

    Optional<Article> findArticleById(int articleId);
}
