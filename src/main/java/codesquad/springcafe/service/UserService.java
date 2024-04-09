package codesquad.springcafe.service;

import codesquad.springcafe.repository.UserRepository;
import codesquad.springcafe.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userDatabase;

    public UserService(UserRepository userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void join(User user) {
        userDatabase.saveUser(user);
    }

    public List<User> findAllUsers() {
        return userDatabase.findAllUsers();
    }

}
