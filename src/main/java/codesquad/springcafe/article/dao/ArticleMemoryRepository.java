package codesquad.springcafe.article.dao;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.IArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ArticleMemoryRepository implements IArticleRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<Integer, Article> articles = new HashMap<>();
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void save(Article article) {
        articles.put(atomicInteger.incrementAndGet(), article);
        log.debug("게시글 갯수 : {}", articles.size());
    }

    public Collection<Article> findAll() {
        return articles.values();
    }

    public int size() {
        return articles.size();
    }

    public Optional<Article> findBy(int id) {
        return Optional.ofNullable(articles.get(id));
    }
}
