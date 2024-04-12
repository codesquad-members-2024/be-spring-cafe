package codesquad.springcafe.service;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);

    Optional<User> findUserByUserId(String userId);

    void updateUser(String userId, UpdatedUser updatedUser);

    void deleteUser(String userId);

    List<User> findAllUser();
}
