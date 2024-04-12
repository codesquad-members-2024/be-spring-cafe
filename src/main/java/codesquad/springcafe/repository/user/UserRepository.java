package codesquad.springcafe.repository.user;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import codesquad.springcafe.exception.db.UserNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User addUser(User user);

    Optional<User> findUserByUserId(String userId) throws UserNotFoundException;

    String updateUser(String userId, UpdatedUser updatedUser) throws UserNotFoundException;

    String deleteUser(String userId);

    List<User> findAllUser();
}
