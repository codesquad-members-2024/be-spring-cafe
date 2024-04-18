package codesquad.springcafe.database.article;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleDatabase {
    Article add(Article article);

    Optional<Article> findBy(Long id);

    List<Article> findAll();

    void update(Article article);

//    void delete(Long id);

    void clear();
}
