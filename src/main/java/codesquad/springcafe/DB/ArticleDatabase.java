package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;

import java.util.List;
import java.util.ArrayList;

public class ArticleDatabase {
    private static final List<Article> articles = new ArrayList<>();

    public static void saveArticle(Article article) {
        articles.add(article);
    }

    public static List<Article> getAllArticles() {
        return articles;
    }

    public static int getArticleSize() {
        return articles.size();
    }

//    public static Article getArticle(int index) {
//        return articles.get(index - 1);
//    }

}
