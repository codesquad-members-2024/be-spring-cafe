package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final Map<String, Article> articleDB = new ConcurrentHashMap<>();

    @Override
    public void add(Article article) {
        articleDB.put(article.getIdentifier(), article);
    }

    @Override
    public List<Article> getArticles() {
        return new ArrayList<>(articleDB.values());
    }

    @Override
    public Article get(String identifier) throws NoSuchArticleException {
        Article article = articleDB.get(identifier);
        if (article == null) throw new NoSuchArticleException();

        return article;
    }

    @Override
    public void update(Article article) throws NoSuchArticleException {

    }

    @Override
    public void delete(String identifier) throws NoSuchArticleException {

    }
}
