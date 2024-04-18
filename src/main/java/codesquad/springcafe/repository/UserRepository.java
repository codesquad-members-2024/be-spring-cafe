package codesquad.springcafe.repository;


import codesquad.springcafe.dto.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    User save(User user);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
}
