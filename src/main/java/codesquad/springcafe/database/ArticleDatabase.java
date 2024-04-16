package codesquad.springcafe.database;

import codesquad.springcafe.Article;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ArticleDatabase {

    private final static Map<Long, Article> articleMap = new LinkedHashMap<>();

    public static void addArticle(Article article) {
        articleMap.put(article.getArticleId(), article);
    }

    public static List<Article> getArticleList() {
        return new ArrayList<>(articleMap.values());
    }

    public static List<Article> getReversedArticleList() {
        return new ArrayList<>(articleMap.values()).reversed();
    }

    public static Article getArticle(long articleId) {
        return articleMap.get(articleId);
    }
}
