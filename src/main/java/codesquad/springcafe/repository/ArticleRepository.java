package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {

    private final List<Article> articles;

    public ArticleRepository() {
        this.articles = new ArrayList<>();
    }

    public void save(Article article) {
        articles.add(article);
    }
}
