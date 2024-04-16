package codesquad.springcafe.repository.user;

import codesquad.springcafe.exception.db.UserNotFoundException;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User addUser(User user);

    Optional<User> findUserByUserId(String userId) throws UserNotFoundException;

    String updateUser(String userId, UpdatedUser updatedUser) throws UserNotFoundException;

    String deleteUser(String userId);

    List<User> findAllUser();

    Optional<SessionUser> findSessionUserByUserId(String userId) throws UserNotFoundException;
}
