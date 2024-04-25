package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
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
        Article article = articles.get(id);
        if (article == null || !article.isValid()) {
            return Optional.empty();
        }
        return Optional.of(article);
    }

    /**
     * 수정 필요
     * **/
    @Override
    public List<Reply> getRepliesById(Long id) {
        return new ArrayList<>();
    }

    @Override
    public List<Article> getAll() {
        return new ArrayList<>(articles.values().stream().filter(Article::isValid).toList());
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
