package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles;
    private Long id = 0L;

    public MemoryArticleRepository() {
        this.articles = new ArrayList<>();
    }

    @Override
    public void addArticle(Article article) {
        article.setId(++id);
        articles.add(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get((int) (id - 1)));
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }
}
