package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private long id = 0;

    @Override
    public Long createArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity();
        article.setId(++id);
        articles.put(id, article);
        return id;
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public void clear() {
        articles.clear();
        this.id = 0L;
    }
}