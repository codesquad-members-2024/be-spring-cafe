package codesquad.springcafe.article.database;


import codesquad.springcafe.article.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleInMemoryDatabase implements ArticleDatabase {

    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setArticleId((long) articles.size() + 1);
        articles.add(article);
    }

    @Override
    public Article findById(Long id) {
        return articles.stream()
            .filter(article -> article.getArticleId() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다."));
    }

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public void clear() {
        articles.clear();
    }

    @Override
    public void update(Article article, Long articleId) {
        Article foundArticle = findById(articleId);
        foundArticle.update(article);
    }
}
