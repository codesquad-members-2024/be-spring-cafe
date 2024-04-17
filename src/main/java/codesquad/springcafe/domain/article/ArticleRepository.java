package codesquad.springcafe.domain.article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
}
