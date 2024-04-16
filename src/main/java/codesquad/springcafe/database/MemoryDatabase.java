package codesquad.springcafe.database;

import codesquad.springcafe.article.domain.Article;
import codesquad.springcafe.database.firstcollection.Articles;
import codesquad.springcafe.exceptions.NoSuchArticleException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.database.firstcollection.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemoryDatabase implements Database{

    private static final Users userDatabase = new Users();
    private static final Articles articleDatabase = new Articles();

    @Override
    public void addUser(User user) {
        userDatabase.put(user);
    }

    @Override
    public List<User> getUsersAsList() {
        return userDatabase.getUsers();
    }

    @Override
    public User findUserByName(String name) throws NoSuchUserException{
        Optional<User> user = userDatabase.getUser(name);
        if (user.isEmpty()) {
            throw new NoSuchUserException();
        }

        return user.get();
    }

    @Override
    public void addArticle(Article article) {
        articleDatabase.add(article);
    }

    @Override
    public List<Article> getArticlesAsList() {
        return articleDatabase.getArticles();
    }

    @Override
    public Article findArticleByIdentifier(String identifier) throws NoSuchArticleException {
        Optional<Article> article = articleDatabase.findArticle(identifier);
        if (article.isEmpty()){
            throw new NoSuchArticleException();
        }
        return article.get();
    }


}
