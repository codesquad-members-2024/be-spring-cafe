package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.EditArticleForm;

public interface ArticleRepository extends DbRepository<Article>{
    Article getById(Long id);
    void edit(String articleId, EditArticleForm editArticleForm);
    void delete(String articleId);
}