package springcafe.article.repository;

import springcafe.article.model.Article;

import java.util.Map;

public interface ArticleRepository {

    public void insert(Article article);
    public Article findById(Long id);
    public Map<Long, Article> findAll();

}
