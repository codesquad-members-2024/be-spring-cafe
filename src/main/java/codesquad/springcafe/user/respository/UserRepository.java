package codesquad.springcafe.user.respository;

import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.User;

import java.util.List;

public interface UserRepository {
    void storeUser(User user);

    List<User> getAllUsers();

    User findByName(String name) throws NoSuchUserException;

    User findById(String id) throws NoSuchUserException;

    void removeUser(String name);

    boolean isIdExist(String value);

    boolean isNameExist(String value);

    void updateUser(User user);

}
