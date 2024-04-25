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
        article.setId(id);
        articles.put(id, article);
    }

    @Override
    public List<Article> findAllArticles() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public Optional<Article> findById(long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public void updateViews(long id) {
        Optional<Article> optionalArticle = findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setViews(article.getViews() + 1);
        }
    }

    @Override
    public void updateArticle(long id, ArticleDto articleDto) {
        Optional<Article> optionalArticle = findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.update(articleDto);
        }
    }

    @Override
    public void deleteArticle(long id) {
        Optional<Article> optionalArticle = findById(id);
        if (optionalArticle.isPresent()) {
            articles.remove(id);
        }
    }
}