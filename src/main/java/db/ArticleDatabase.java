package db;

import model.Article;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public class ArticleDatabase {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);

    private static ArrayList<Article> articles = new ArrayList<>();

    public static int getArticleID() {   // Article에 ID를 주기 위한 메소드
        return articles.size() + 1;    // index는 1번부터 시작하도록 +1
    }

    public static void addArticle(Article article) {
        articles.add(0, article);   // 들어온 순으로 저장하기 위해 0부터 저장
        // Stack으로 바꾸어도 가능
        logger.debug("Article [{}] Added , Total Articles : {}", article, articles.size());
    }

    public static ArrayList<Article> getAllArticles() {
        return articles;
    }

    public static Optional<Article> findArticleById(int articleId) {
        if (articleId >= 0 && articleId < articles.size()) {
            return Optional.of(articles.get(articleId));
        }
        logger.error("Article ID : {}, But TotalArticles : {}", articleId, articles.size());
        return Optional.empty();
    }
}
