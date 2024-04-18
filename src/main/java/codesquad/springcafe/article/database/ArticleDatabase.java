package codesquad.springcafe.article.database;

import codesquad.springcafe.article.Article;
import java.util.List;

public interface ArticleDatabase {

    void save(Article article);

    Article findById(Long articleId);

    List<Article> findAll();

    void update(Article article, Long articleId);

    void clear();
}
