package codesquad.springcafe.article;

import java.util.List;

public interface ArticleDatabase {

    void save(Article article);

    Article findById(long articleId);

    List<Article> findAll();

    void clear();
}
