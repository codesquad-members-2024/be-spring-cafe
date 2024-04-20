package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void addArticle(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll();
}
