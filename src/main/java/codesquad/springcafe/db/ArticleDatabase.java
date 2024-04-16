package codesquad.springcafe.db;

import codesquad.springcafe.articles.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class ArticleDatabase {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);

    private Map<Integer, Article> articles = new LinkedHashMap<>();

    public int getArticleID() {   // Article에 ID를 주기 위한 메소드
        return articles.size() + 1;    // index는 1번부터 시작하도록 +1
    }

    public void addArticle(Article article) {
        int articleId = getArticleID();
        article.setArticleId(articleId);    // memory에서는 직접 setter를 통해 할당
        articles.put(articleId, article);
        logger.debug("Article # {} ,[{}] Added at Memory DB", articleId, article);
    }

    public ArrayList<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public Article findArticleById(int articleId) {
        logger.debug("articleId  :{}", articleId);
        if (articleId >= 0 && articleId <= articles.size()) {
            return articles.get(articleId);
        }
        return null;
    }
}
