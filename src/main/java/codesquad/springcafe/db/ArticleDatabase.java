package codesquad.springcafe.db;

import codesquad.springcafe.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDatabase {

    public void addArticle(Article article);

    public List<Article> findAllArticles();

    public Optional<Article> findArticleBySequence(long sequence);

    public void clearDatabase();

    public int getTotalArticleNumber();
}
