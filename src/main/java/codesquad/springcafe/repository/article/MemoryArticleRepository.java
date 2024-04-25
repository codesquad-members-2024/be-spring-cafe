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
        return Optional.ofNullable(articles.get(id));
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
        return new ArrayList<>(articles.values());
    }

    @Override
    public void modify(Article modifiedArticle) {
        articles.put(modifiedArticle.getId(), modifiedArticle);
    }

    @Override
    public void remove(Long id) {
        articles.remove(id);
    }
}
