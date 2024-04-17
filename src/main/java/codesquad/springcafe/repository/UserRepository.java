package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Optional<User> findByUserId(String userId);
}
