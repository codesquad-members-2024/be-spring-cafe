package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static final List<Article> articles = new ArrayList<>();
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public static User getUser(String userId) {
        Optional<User> user = Optional.ofNullable(users.get(userId));
        return user.orElseThrow();
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
