package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.Article;

public interface ArticleRepository extends DbRepository<Article>{
    Article getById(Long id);
}