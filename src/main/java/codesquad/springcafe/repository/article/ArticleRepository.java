package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.article.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findById(long id);

    List<Article> findAll();

    void clear();
}
