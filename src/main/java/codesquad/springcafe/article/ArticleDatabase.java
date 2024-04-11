package codesquad.springcafe.article;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDatabase {

    private final List<Article> articles = new ArrayList<>();

    public void saveArticle(Article article) {
        articles.add(article);
    }

    public List<Article> findAll() {
        return articles;
    }

    public void clear() {
        articles.clear();
    }


}
