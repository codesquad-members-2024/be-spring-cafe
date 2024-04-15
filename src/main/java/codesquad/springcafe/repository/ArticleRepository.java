package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;

import java.util.List;

public interface ArticleRepository {

    Long save(Article article);

    Article findById(Long articleId);

    List<Article> findAllArticle();

    public void clear();
}
