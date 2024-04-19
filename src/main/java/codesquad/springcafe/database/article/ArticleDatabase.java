package codesquad.springcafe.database.article;

import codesquad.springcafe.domain.Article;

import java.util.List;

public interface ArticleDatabase {
    void saveArticle(Article article);

    List<Article> getAllArticles();

    Article getArticleById(int id);

    int getArticleSize();

    boolean isArticleEmpty();

    void incrementViewsById(int id);

}
