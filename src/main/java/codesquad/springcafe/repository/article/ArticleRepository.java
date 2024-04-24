package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);

    Optional<Article> getByIndex(Long index);

    List<Article> getAll();
}
