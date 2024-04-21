package codesquad.springcafe.repository;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.model.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {

    private final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Long save(Article article) {
        long articleId = sequence.incrementAndGet();
        article.setArticleId(articleId);
        articles.put(articleId, article);
        return articleId;
    }

    public Article findById(Long articleId) {
        return articles.get(articleId);
    }

    public List<Article> findAllArticle() {
        return new ArrayList<>(articles.values());
    }

    public void clear() {
        articles.clear();
        sequence.set(0L);
    }

    @Override
    public void update(Long articleId, ArticleRequestDto articleRequestDto) {
    }
}
