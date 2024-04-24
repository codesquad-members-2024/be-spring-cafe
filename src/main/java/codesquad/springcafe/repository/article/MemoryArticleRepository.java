package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Long, Article> articles;

    public MemoryArticleRepository() {
        articles = new LinkedHashMap<>();
    }

    @Override
    public void save(Article article) {
        articles.put(article.getId(), article);
    }

    @Override
    public Optional<Article> getById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public List<Article> getAll() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public void modify(Article modifiedArticle) {
        articles.put(modifiedArticle.getId(), modifiedArticle);
    }

    @Override
    public void remove(Long index) {
        articles.remove(index);
    }
}
