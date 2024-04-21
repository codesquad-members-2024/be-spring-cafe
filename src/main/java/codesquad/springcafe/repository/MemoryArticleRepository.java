package codesquad.springcafe.repository;

import codesquad.springcafe.domain.db.ArticleDatabase;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.repository.ArticleRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final ArticleDatabase database;

    public MemoryArticleRepository(ArticleDatabase database) {
        this.database = database;
    }

    @Override
    public void add(Article article) {
        database.addArticle(article);
    }

    @Override
    public List<Article> getAll() {
        return database.getAllArticles();
    }

    @Override
    public Article getById(Long id) {
        return database.getArticle(id);
    }
}
