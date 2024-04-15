package codesquad.springcafe.user.service;

import codesquad.springcafe.database.Database;
import codesquad.springcafe.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    void storeUser(User user);

    List<User> getAllUsers();

}
