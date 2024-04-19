package springcafe.article.repository;

import springcafe.article.model.Article;

import java.util.List;

public interface ArticleDao {

    public void insert(Article article);
    public Article findById(Long id);
    public List<Article> findAll();

}
