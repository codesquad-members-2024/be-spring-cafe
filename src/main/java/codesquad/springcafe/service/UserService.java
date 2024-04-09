package codesquad.springcafe.service;

import codesquad.springcafe.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signup(User user);
    Optional<User> findUserById(Long id);
    List<User> findAllUsers();
}
