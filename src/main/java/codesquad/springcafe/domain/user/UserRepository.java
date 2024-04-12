package codesquad.springcafe.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public void save(User user) {
        user.setId((long) (users.size() + 1));
        users.put(user.getUserId(), user);
    }

    public void update(User user) {
        users.put(user.getUserId(), user);
    }

    public User findByUserId(String userId) {
        return users.get(userId);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
