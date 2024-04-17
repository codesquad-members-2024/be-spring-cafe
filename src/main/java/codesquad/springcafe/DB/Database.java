package codesquad.springcafe.DB;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import java.util.List;

public interface Database {
    public void addUser(User user);

    public List<User> getAllUsers();

    public User getUser(String userId);

    public void addArticle(Article article);

    public List<Article> getAllArticles();

    public Article getArticle(Long index);

    public int articlesSize();
    public void updateUser(User user);
}
