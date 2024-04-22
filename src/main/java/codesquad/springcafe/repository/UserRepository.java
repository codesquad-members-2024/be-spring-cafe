package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(User user);

    List<User> findAllUsers();

    Optional<User> findUserById(String userId);

    void clear();

    void update(User user);

}
