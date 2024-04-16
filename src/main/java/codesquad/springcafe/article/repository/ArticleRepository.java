package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.database.Database;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    private final Database database;

    @Autowired
    public ArticleRepository(Database database) {
        this.database = database;
    }

    public void add(Article article) {
        database.addArticle(article);
    }

    public List<Article> getArticles() {
        return database.getArticlesAsList();
    }

    public Article getArticle(String identifier) throws NoSuchArticleException {
        return database.findArticleByIdentifier(identifier);
    }
}
