package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;

import java.util.List;

public interface ArticleRepository {

    void add(Article article) throws IllegalArgumentException;
    Article findById(int id);
    List<Article> findAll();
    void addPoint(Article article);
}
