package codesquad.springcafe.articles.repository;

import codesquad.springcafe.exception.ArticleNotFoundException;
import db.ArticleDatabase;
import model.Article;
import model.ArticleData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MemoryArticleRepository implements ArticleRepository{
    private static final Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

    @Override
    public void createArticle(ArticleData articleData) {
        String userId = articleData.getUserId();
        String title = articleData.getTitle();
        String content = articleData.getContent();

        Article article = new Article(userId, title, content);
        logger.debug("Article Created : {}", article);

        ArticleDatabase.addArticle(article);
    }

    @Override
    public ArrayList<Article> getAllArticles() {
        return ArticleDatabase.getAllArticles();
    }

    @Override
    public Article findArticleById(int articleId) {
        articleId = articleId - 1;
        return ArticleDatabase.findArticleById(articleId).orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }
}
