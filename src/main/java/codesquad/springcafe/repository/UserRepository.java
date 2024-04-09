package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User createUser(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAllUsers() {
        return users;
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
