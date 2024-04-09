package codesquad.springcafe.database.article;

import codesquad.springcafe.model.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleMemoryDatabase implements ArticleDatabase {
    private final Map<Long, Article> store = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public Article save(Article article) {
        article.setId(++id);
        store.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<Article> findBy(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
