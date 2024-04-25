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

    @Override
    public void createArticle(Article article) {
        long id = articles.size() + 1;
        article.setArticleId(id);
        articles.put(id, article);
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findByArticleId(long articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

    @Override
    public void updateViews(long articleId) {
        Optional<Article> optionalArticle = findByArticleId(articleId);
        optionalArticle.ifPresent(article -> article.setViews(article.getViews() + 1));
    }

    @Override
    public void updateArticle(long articleId, ArticleDto articleDto) {
        Optional<Article> optionalArticle = findByArticleId(articleId);
        optionalArticle.ifPresent(article -> article.update(articleDto));
    }

    @Override
    public void deleteArticle(long articleId) {
        Optional<Article> optionalArticle = findByArticleId(articleId);
        optionalArticle.ifPresent(article -> articles.remove(articleId));
    }
}