package codesquad.springcafe.database.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.UpdateArticle;

import java.util.List;

public interface ArticleDatabase {
    void saveArticle(Article article);

    public void updateArticle(int id, UpdateArticle updateArticle);

    public void deleteArticle(int id);

    List<Article> getAllArticles();

    Article getArticleById(int id);

    int getArticleSize();

    boolean isArticleEmpty();

    void incrementViewsById(int id);

}
