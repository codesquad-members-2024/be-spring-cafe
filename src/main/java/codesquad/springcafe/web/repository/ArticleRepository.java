package codesquad.springcafe.web.repository;

import codesquad.springcafe.web.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    List<Article> articlesAll();

    Optional<Article> findByIndex(int number);

}
