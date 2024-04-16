package codesquad.springcafe.articles.repository;

import db.ArticleDatabase;
import model.article.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    @Override
    public void createArticle(Article article) {
        ArticleDatabase.addArticle(article);
    }

    @Override
    public Optional<ArrayList<Article>> getAllArticles() {
        return Optional.of(ArticleDatabase.getAllArticles());
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        return Optional.ofNullable(ArticleDatabase.findArticleById(articleId));
    }
}
