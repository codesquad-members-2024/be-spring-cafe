package codesquad.springcafe.repository;

import codesquad.springcafe.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {

    private final List<Article> articles;

    public ArticleRepository() {
        this.articles = new ArrayList<>();
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public Article findByIndex(int id) {
        return articles.get(id);
    }

    public List<Article> findAll() {
        return articles;
    }
}
