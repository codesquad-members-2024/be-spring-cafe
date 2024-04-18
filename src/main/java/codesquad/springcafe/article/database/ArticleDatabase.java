package codesquad.springcafe.article.database;

import codesquad.springcafe.article.Article;
import java.util.List;

public interface ArticleDatabase {

    void save(Article article);

    Article findById(long articleId);

    List<Article> findAll();

    void clear();
}
