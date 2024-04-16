package springcafe.article.repository;

import org.springframework.stereotype.Repository;
import springcafe.article.model.Article;


import java.util.*;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{

   private static Map<Long, Article> articleRepository = new LinkedHashMap<>();

    @Override
    public void save(Article article) {
        articleRepository.put(article.getId(), article);
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.get(id);

    }

    @Override
    public Map<Long, Article> findAll() {
        return Collections.unmodifiableMap(articleRepository);
    }

}
