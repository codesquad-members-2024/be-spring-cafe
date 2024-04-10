package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static final List<User> users = new ArrayList<>();

    public User create(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findAny();
    }
}