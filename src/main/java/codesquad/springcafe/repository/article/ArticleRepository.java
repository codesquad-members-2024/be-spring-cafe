package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.util.PageRequest;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);

    Optional<Article> getById(Long id);

    List<Article> getAll();

    List<Article> getAllByPaging(PageRequest pageRequest);

    void modify(Article modifiedArticle);

    void removeHard(Long id);

    void removeSoft(Long id);
}
