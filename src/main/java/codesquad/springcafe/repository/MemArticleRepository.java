package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemArticleRepository implements ArticleRepository{

    Map<Integer , Article> articles = new ConcurrentHashMap<>();


    @Override
    public void add(Article article) throws IllegalArgumentException {
        article.setId(articles.size() + 1);
        article.setCreatedDateTime(LocalDateTime.now());

        articles.put(articles.size() + 1 , article);
    }

    @Override
    public Article findById(int id) {
        return articles.get(id);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }
}
