package codesquad.springcafe.web.repository;

import codesquad.springcafe.web.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
//    private static List<User> users = new ArrayList<>();
    private static Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void userUpdate(User user) {
        users.put(user.getUserId(), user);
    }

}
