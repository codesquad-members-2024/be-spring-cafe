package codesquad.springcafe.domain.repository;

import codesquad.springcafe.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends DbRepository<User> {
    void update(User user);
    Optional<User> getById(String userId);
    List<User> getAll();
}
