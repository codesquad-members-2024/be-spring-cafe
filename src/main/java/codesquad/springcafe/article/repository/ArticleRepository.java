package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticlePostReq;

import java.util.List;

public interface ArticleRepository {

    void add(ArticlePostReq articlePostReq) throws IllegalArgumentException;
    Article findById(int id);
    List<Article> findAll();
    void addPoint(Article article);
}
