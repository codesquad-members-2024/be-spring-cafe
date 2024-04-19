package codesquad.springcafe.article.dao;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ArticleMemoryDao implements ArticleDao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<Integer, Article> articles = new HashMap<>();
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void save(Article article) {
        articles.put(atomicInteger.incrementAndGet(), article);
        log.debug("게시글 갯수 : {}", articles.size());
    }

    @Override
    public Collection<Article> findAll() {
        return articles.values();
    }


    @Override
    public Optional<Article> findBy(int id) {
        return Optional.ofNullable(articles.get(id));
    }
}
