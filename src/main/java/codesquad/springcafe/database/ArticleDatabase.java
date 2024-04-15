package codesquad.springcafe.database;

import codesquad.springcafe.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDatabase {

    private final static List<Article> articleList = new ArrayList<>();

    public static void addArticle(Article article) {
        articleList.add(article);
    }

    public static List<Article> getArticleList() {
        return articleList;
    }

    public static List<Article> getReversedArticleList() {
        return articleList.reversed();
    }
}
