package codesquad.springcafe.domain.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> users;

    public MemoryUserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public void save(User user) {
        user.setId((long) (users.size() + 1));
        users.put(user.getUserId(), user);
    }

    @Override
    public void update(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String userId) {
        return users.get(userId);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
