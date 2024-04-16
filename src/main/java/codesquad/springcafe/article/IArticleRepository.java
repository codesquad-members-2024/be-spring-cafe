package codesquad.springcafe.article;

import codesquad.springcafe.article.Article;

import java.util.Collection;
import java.util.Optional;

public interface IArticleRepository {

    void save(Article article);

    Collection<Article> findAll();

    int size();

    Optional<Article> findBy(int id);
}
