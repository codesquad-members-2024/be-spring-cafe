package codesquad.springcafe.article;

import java.util.Collection;
import java.util.Optional;

public interface ArticleDao {

    void save(Article article);

    Collection<Article> findAll();

    Optional<Article> findBy(int id);

    void update(Article article);

    void delete(String writer, Long id);
}
