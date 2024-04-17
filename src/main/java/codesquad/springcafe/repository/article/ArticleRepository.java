package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAll();

    Optional<Article> findById(Long id);
}