package codesquad.springcafe.database;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;

import java.util.List;

public interface Database {

    /** User **/
    void addUser(User user);
    List<User> getUsersAsList();
    User findUserByName(String name) throws NoSuchUserException;

    void addArticle(Article article);

    /** Article **/
    List<Article> getArticlesAsList();
    Article findArticleByIdentifier(String identifier) throws NoSuchArticleException;

}
