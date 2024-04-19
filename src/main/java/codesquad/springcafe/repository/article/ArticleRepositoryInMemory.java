package codesquad.springcafe.repository.article;

import codesquad.springcafe.controller.article.UpdateArticle;
import codesquad.springcafe.domain.article.Article;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryInMemory implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryInMemory.class);
    private final Map<Long, Article> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong();

    @Override
    public Article save(Article article) {
        long createdId = sequence.incrementAndGet();
        article.setId(createdId);
        store.put(createdId, article);
        logger.info("saved article={}", article.getTitle());

        return article;
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Article> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Article::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public void update(UpdateArticle updateParam) {
        Article article = store.get(updateParam.getId());
        article.setTitle(updateParam.getTitle());
        article.setContents(updateParam.getContents());
    }

    @Override
    public void delete(long id) {
        store.remove(id);
    }

    @Override
    public void clear() {
        store.clear();
        sequence.set(0L);
    }
}
