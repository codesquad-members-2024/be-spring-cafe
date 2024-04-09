package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import java.util.Optional;

public interface UserService {

    void createUser(User user);

    Optional<User> findUserById(String userId);
}
