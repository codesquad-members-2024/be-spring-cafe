package codesquad.springcafe.database;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;

import java.util.List;

public interface Database {

    void addUser(User user);
    List<User> getUsersAsList();

    User findUserByName(String name) throws NoSuchUserException;
}
