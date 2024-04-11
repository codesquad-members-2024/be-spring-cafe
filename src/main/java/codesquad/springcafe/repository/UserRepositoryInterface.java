package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {
    User createUser(User user);

    List<User> findAllUsers();

    Optional<User> findByUserId(String userId);
}
