package codesquad.springcafe.service;

import codesquad.springcafe.domain.User;
import java.util.List;

public interface UserService {

    void createUser(User user);
    List<User> findAllUsers();
}
