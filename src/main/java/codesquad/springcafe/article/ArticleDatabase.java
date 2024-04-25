package codesquad.springcafe.article;

import java.util.List;

public interface ArticleDatabase {

    public void addArticle(Article article);

    public List<Article> getArticleList();

    public List<Article> getReversedArticleList();

    public Article getArticle(long articleId);

    public void updateArticle(Article article);

    public void deleteArticle(long articleId);
}
