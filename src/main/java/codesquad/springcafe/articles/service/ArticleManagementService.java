package codesquad.springcafe.articles.service;

import codesquad.springcafe.users.service.UserManagementService;
import db.ArticleDatabase;
import model.Article;
import model.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArticleManagementService implements ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleManagementService.class);

    @Override
    public void createArticle(ArticleVO articleVO) {
        String userId = articleVO.getUserId();
        String title = articleVO.getTitle();
        String content = articleVO.getContent();

        Article article = new Article(userId, title, content);
        logger.debug("Article Created : {}", article);

        ArticleDatabase.addArticle(article);
    }

    @Override
    public ArrayList<Article> getAllArticles() {
        return ArticleDatabase.getAllArticles();
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        articleId = articleId - 1;
        return ArticleDatabase.findArticleById(articleId);
    }
}
