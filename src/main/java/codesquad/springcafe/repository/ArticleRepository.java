package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> articles;
    private final AtomicLong sequence;

    public ArticleRepository() {
        this.articles = new HashMap<>();
        this.sequence = new AtomicLong();
    }

    public void save(Article article) {
        articles.put(sequence.getAndIncrement(), article);
    }

    public Article findByIndex(Long index) {
        return articles.get(index - 1);
    }

    public Map<Long, Article> getArticles() {
        return Collections.unmodifiableMap(articles);
    }
}
