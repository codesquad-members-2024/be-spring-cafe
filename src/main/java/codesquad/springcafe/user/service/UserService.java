package codesquad.springcafe.user.service;

import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.user.domain.LoginUser;
import codesquad.springcafe.user.domain.User;
import codesquad.springcafe.user.domain.UserIdentity;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> getAllUsersAsList();

    User findUserByName(String name) throws NoSuchUserException;

    User findUserById(String id) throws NoSuchUserException;

    UserIdentity loginVerification(LoginUser loginUser) throws CanNotLoginException;

    void updateUser(User after);

    boolean checkValueIsDuplicate(String key, String value);

}
