package codesquad.springcafe.database;

import codesquad.springcafe.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDatabase {

    private final static List<Article> articlelist = new ArrayList<>();

    public static void addArticle(Article article) {
        articlelist.add(article);
    }

    public static List<Article> getArticleList() {
        return articlelist;
    }

}
