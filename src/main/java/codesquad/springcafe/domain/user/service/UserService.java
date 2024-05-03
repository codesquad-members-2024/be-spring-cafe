package codesquad.springcafe.domain.user.service;

import codesquad.springcafe.domain.user.dto.User;
import codesquad.springcafe.domain.user.dto.UserIdentity;
import codesquad.springcafe.exceptions.CanNotLoginException;
import codesquad.springcafe.exceptions.NoSuchUserException;
import codesquad.springcafe.domain.user.dto.LoginUser;

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
