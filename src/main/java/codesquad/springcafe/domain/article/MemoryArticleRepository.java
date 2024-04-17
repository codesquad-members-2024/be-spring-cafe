package codesquad.springcafe.domain.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles;

    public MemoryArticleRepository() {
        this.articles = new ArrayList<>();
    }

    @Override
    public void save(Article article) {
        articles.add(article);
    }

    @Override
    public Article findById(Long id) {
        return articles.get((int) (id - 1));
    }

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }
}
