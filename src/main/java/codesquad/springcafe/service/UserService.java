package codesquad.springcafe.service;

import codesquad.springcafe.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public User createUser(String userId, String password, String name, String email) {
        User newUser = new User(userId, password, name, email);
        users.add(newUser);
        return newUser;
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(users);
    }
}
