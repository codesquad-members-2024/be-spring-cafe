package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Database {
    private static final List<User> users = new ArrayList<>();
    private static final List<Article> articles = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static User getUser(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public static void addArticle(Article article) {
        articles.add(article);
    }

    public static List<Article> getAllArticles() {
        return articles;
    }

    public static int articlesSize() {
        return articles.size();
    }

    public static Article getArticle(int index) {
        return articles.get(index - 1);
    }
}
