package codesquad.springcafe.service;

import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.exception.service.UserNotJoinedException;
import codesquad.springcafe.model.ListUser;
import codesquad.springcafe.model.LoginUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import java.util.List;

public interface UserService {
    void addUser(User user) throws DuplicateUserIdException;

    User findUserByUserId(String userId);

    void updateUser(String userId, UpdatedUser updatedUser);

    void deleteUser(String userId);

    List<ListUser> findAllUser();

    boolean isJoinedUser(LoginUser loginUser) throws UserNotJoinedException;

    SessionUser findSessionUserById(String userId);
}
