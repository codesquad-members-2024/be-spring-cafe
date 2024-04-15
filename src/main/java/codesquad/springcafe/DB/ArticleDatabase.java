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

    public static Article getArticleById(int id) {
        return articles.get(id-1);
    }

    public static int getArticleSize() {
        return articles.size();
    }

    public static boolean isArticleEmpty(){
        return articles.isEmpty();
    }

}
