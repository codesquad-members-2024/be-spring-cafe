package codesquad.springcafe.web.repository;

import codesquad.springcafe.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByName(String userName);

    List<User> findAll();
}
