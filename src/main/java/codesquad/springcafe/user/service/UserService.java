package codesquad.springcafe.user.service;

import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.LoginUser;
import codesquad.springcafe.user.domain.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> getAllUsersAsList();

    User findUserByName(String name) throws NoSuchUserException;

    User findUserById(String id) throws NoSuchUserException;

    void loginVerification(LoginUser loginUser) throws CanNotLoginException;

    void updateUser(User before, User after);

    boolean checkValueIsDuplicate(String value);

}
