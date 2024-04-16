package springcafe.article.repository;

import springcafe.article.model.Article;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {

    public void save(Article article);
    public Article findById(Long id);
    public Map<Long, Article> findAll();

}
