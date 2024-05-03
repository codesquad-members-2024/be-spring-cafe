package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.EditArticleForm;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends DbRepository<Article>{
    Optional<Article> getById(Long id);
    void edit(String articleId, EditArticleForm editArticleForm);
    void delete(String articleId);
    List<Article> getAll();
}