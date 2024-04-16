package codesquad.springcafe.database;

import codesquad.springcafe.Article;

import java.util.List;

public interface ArticleDatabase {

    public void addArticle(Article article);

    public List<Article> getArticleList();

    public List<Article> getReversedArticleList();

    public Article getArticle(long articleId);
}
