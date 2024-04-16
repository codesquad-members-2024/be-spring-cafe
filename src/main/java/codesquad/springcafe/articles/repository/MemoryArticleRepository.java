package codesquad.springcafe.articles.repository;

import codesquad.springcafe.db.ArticleDatabase;
import codesquad.springcafe.model.article.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Primary
@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    private final ArticleDatabase articleDatabase;

    @Autowired
    public MemoryArticleRepository(ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @Override
    public void createArticle(Article article) {
        articleDatabase.addArticle(article);
    }

    @Override
    public Optional<ArrayList<Article>> getAllArticles() {
        return Optional.of(articleDatabase.getAllArticles());
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        return Optional.ofNullable(articleDatabase.findArticleById(articleId));
    }
}
