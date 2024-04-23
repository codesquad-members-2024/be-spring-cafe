package codesquad.springcafe.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);
    void update(User user);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
}
