package codesquad.springcafe.db.article;

import codesquad.springcafe.model.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDatabase {

    public void addArticle(Article article);

    public void update(long sequence, Article updatedArticle);

    public List<Article> findAllArticles();

    public Optional<Article> findArticleBySequence(long sequence);

    public void clearDatabase();

    public int getTotalArticleNumber();
}
