package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private Long articleId = 0L;

    @Override
    public Article createArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity(++articleId);
        articles.put(articleId, article);
        return article;
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findByArticleId(Long articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }
}