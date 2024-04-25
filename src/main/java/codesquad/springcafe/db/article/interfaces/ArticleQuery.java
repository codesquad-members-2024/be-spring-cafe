package codesquad.springcafe.db.article.interfaces;

import codesquad.springcafe.model.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleQuery {
    public List<Article> findAllArticles();
    public Optional<Article> findArticleBySequence(long sequence);
    public int getTotalArticleNumber();
}
