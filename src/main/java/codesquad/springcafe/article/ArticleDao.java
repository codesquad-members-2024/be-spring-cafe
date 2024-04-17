package codesquad.springcafe.article;

import java.util.Collection;
import java.util.Optional;

public interface ArticleDao {

    void save(Article article);

    Collection<Article> findAll();

    int size();

    Optional<Article> findBy(int id);
}
