package codesquad.springcafe.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ArticleRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final Map<Integer, Article> articles = new HashMap<>();

    public void save(int id, Article article) {
        articles.put(id, article);
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
