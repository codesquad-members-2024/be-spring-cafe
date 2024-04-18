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

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Map<Long, Article> articles = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public Article createArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity();
        article.setId(++id);
        articles.put(id, article);
        return article;
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }
}