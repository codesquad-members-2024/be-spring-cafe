package codesquad.springcafe.article;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDatabase {

    private final List<Article> articles = new ArrayList<>();

    public void saveArticle(Article article) {
        article.setArticleId(articles.size() + 1);
        articles.add(article);
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    public Article findById(long id) {
        return articles.stream()
            .filter(article -> article.getArticleId() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다."));
    }

    public void clear() {
        articles.clear();
    }
}
