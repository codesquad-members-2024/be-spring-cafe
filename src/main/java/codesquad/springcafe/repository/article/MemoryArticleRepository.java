package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Article findById(long id) {
        return articles.get(id);
    }

    @Override
    public void updateViews(long id) {
        Article article = articles.get(id);
        article.setViews(article.getViews() + 1);
    }

    @Override
    public void updateArticle(long id, ArticleDto articleDto) {
        Article article = articles.get(id);
        article.update(articleDto);
    }

    @Override
    public void deleteArticle(long id) {
        articles.remove(id);
    }
}