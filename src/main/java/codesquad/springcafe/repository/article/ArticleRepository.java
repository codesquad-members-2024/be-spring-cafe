package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository implements ArticleRepositoryInterface {
    private static final List<Article> articles = new ArrayList<>();
    private Long articleId = 0L;

    @Override
    public Article createArticle(Article article) {
        article.setArticleId(articleId++);
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> findAllArticles() {
        return articles;
    }

    @Override
    public Optional<Article> findByArticleId(Long articleId) {
        return articles.stream().filter(article -> article.getArticleId().equals(articleId)).findFirst();
    }
}
