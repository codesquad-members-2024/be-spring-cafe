package codesquad.springcafe.web.repository;

import codesquad.springcafe.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
    void userUpdate(User user);
}
