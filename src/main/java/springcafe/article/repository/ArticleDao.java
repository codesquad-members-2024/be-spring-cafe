package springcafe.article.repository;

import springcafe.article.model.Article;

import java.util.List;

public interface ArticleDao {

    public void save(Article article, Long id);
    public Article findById(Long id);
    public List<Article> findAll();
    public void update(Article article);
    public void delete(Long articleId);

}
