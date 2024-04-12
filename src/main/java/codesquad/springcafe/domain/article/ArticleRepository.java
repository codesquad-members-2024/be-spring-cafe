package codesquad.springcafe.domain.article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
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

    public Article findBySequence(int sequence) {
        return articles.get(sequence - 1);
    }

    public List<Article> getArticles() {
        return Collections.unmodifiableList(articles);
    }
}
