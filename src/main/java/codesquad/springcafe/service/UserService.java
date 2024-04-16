package codesquad.springcafe.service;

import codesquad.springcafe.exception.service.DuplicateUserIdException;
import codesquad.springcafe.model.LoginUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user) throws DuplicateUserIdException;

    Optional<User> findUserByUserId(String userId);

    void updateUser(String userId, UpdatedUser updatedUser);

    void deleteUser(String userId);

    List<User> findAllUser();

    boolean isJoinedUser(LoginUser loginUser);

    Optional<SessionUser> findSessionUserById(String userId);
}
