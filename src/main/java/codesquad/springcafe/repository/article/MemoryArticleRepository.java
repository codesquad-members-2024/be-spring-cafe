package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.util.PageRequest;
import java.util.ArrayList;
import java.util.Comparator;
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
    public List<Article> getAll() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public List<Article> getAllByPaging(PageRequest pageRequest) {
        return getAll().stream()
            .filter(Article::isValid)
            .sorted(Comparator.comparing(Article::getId)).toList();
    }

    @Override
    public Optional<Article> getById(Long id) {
        Article article = articles.get(id);
        if (article == null || !article.isValid()) {
            return Optional.empty();
        }
        return Optional.of(article);
    }

    @Override
    public void modify(Article modifiedArticle) {
        if (modifiedArticle.isValid()) {
            articles.put(modifiedArticle.getId(), modifiedArticle);
        }
    }

    @Override
    public void removeHard(Long id) {
        articles.remove(id);
    }

    @Override
    public void removeSoft(Long id) {
        articles.get(id).setDeleted(true);
    }
}
