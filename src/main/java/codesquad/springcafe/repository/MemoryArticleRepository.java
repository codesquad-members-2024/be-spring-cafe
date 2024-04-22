package codesquad.springcafe.repository;

import codesquad.springcafe.domain.db.ArticleDatabase;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.repository.ArticleRepository;
import codesquad.springcafe.dto.EditArticleForm;
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

    @Override
    public void edit(String articleId, EditArticleForm editArticleForm){
        Article target = database.getArticle(Long.parseLong(articleId));
        target.setTitle(editArticleForm.getTitle());
        target.setContents(editArticleForm.getContents());
    }

    @Override
    public void delete(String articleId){
        database.deleteArticle(Long.parseLong(articleId));
    };
}
