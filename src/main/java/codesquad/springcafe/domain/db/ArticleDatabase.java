package codesquad.springcafe.domain.db;

import codesquad.springcafe.domain.Article;
import java.util.List;

public interface ArticleDatabase {
    public void addArticle(Article article);

    public List<Article> getAllArticles();

    public Article getArticle(Long index);

    public int articlesSize();

    public void deleteArticle(Long index);
}
