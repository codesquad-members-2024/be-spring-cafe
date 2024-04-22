package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.db.ArticleDatabase;
import codesquad.springcafe.domain.db.UserDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MyDatabase implements ArticleDatabase, UserDatabase {
    private static final List<Article> articles = new ArrayList<>();
    private static final Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUser(String userId) {
        Optional<User> user = Optional.ofNullable(users.get(userId));
        return user.orElseThrow();
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public int articlesSize() {
        return articles.size();
    }

    public Article getArticle(Long index) {
        if (index >= Integer.MIN_VALUE && index <= Integer.MAX_VALUE) {
            int intValue = index.intValue();
            return articles.get(intValue);
        } else {
            throw new NumberFormatException();
        }
    }

    public void updateUser(User user) {
        String key = user.getUserId();
        User target = users.get(key);

        target.setEmail(user.getEmail());
        target.setName(user.getName());
        target.setPassword(user.getPassword());
    }

    public void deleteArticle(Long index) {
        if (index >= Integer.MIN_VALUE && index <= Integer.MAX_VALUE) {
            int intValue = index.intValue();
            articles.remove(intValue);
        } else {
            throw new NumberFormatException();
        }
    }
}
