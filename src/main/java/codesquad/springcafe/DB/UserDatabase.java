package codesquad.springcafe.DB;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UpdatedUser;

import java.util.List;
import java.util.Optional;

public interface UserDatabase{
    void saveUser(User user);

    void updateUser(String id, UpdatedUser updatedUser);

    List<User> getAllUsers();

    Optional<User> getUserById(String id);

    int getUsersSize();



}
