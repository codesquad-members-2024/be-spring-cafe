package codesquad.springcafe.article.repository;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.database.firstcollection.Articles;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final Articles articleDB = new Articles();

    public void add(Article article) {
        articleDB.add(article);
    }

    public List<Article> getArticles() {
        return articleDB.getArticles();
    }

    public Article getArticle(String identifier) throws NoSuchArticleException {
        Optional<Article> article = articleDB.findArticle(identifier);
        if (article.isEmpty()) throw new NoSuchArticleException();

        return article.get();
    }
}
