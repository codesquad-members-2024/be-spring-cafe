package codesquad.springcafe.service;

import codesquad.springcafe.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signup(User user);
    Optional<User> findUserById(Long id); // null 이 있을 수도 있을 경우... null 안정성
    List<User> findAllUsers();
}
