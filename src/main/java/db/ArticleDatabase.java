package db;

import model.article.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ArticleDatabase {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);

    private static Map<Integer, Article> articles = new LinkedHashMap<>();

    public static int getArticleID() {   // Article에 ID를 주기 위한 메소드
        return articles.size() + 1;    // index는 1번부터 시작하도록 +1
    }

    public static void addArticle(Article article) {
        int articleId = getArticleID();
        articles.put(articleId, article);
        logger.debug("Article # {} ,[{}] Added", articleId, article);
    }

    public static ArrayList<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public static Article findArticleById(int articleId) {
        if (articleId >= 0 && articleId <= articles.size()) {
            return articles.get(articleId);
        }
        return null;
    }
}
