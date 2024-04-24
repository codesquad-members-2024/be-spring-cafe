package codesquad.springcafe.repository.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findById(long id);

    List<Article> findAll();

    Page<Article> findAll(PageRequest pageRequest);

    void update(UpdateArticle updateParam);

    void delete(long id);

    void softDelete(long id);

    void restore(long id);

    void clear();
}
