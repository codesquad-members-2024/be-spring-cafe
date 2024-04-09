package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    void add(Article article) throws IllegalArgumentException;
    Article findById(int id);
    List<Article> findAll();
}
