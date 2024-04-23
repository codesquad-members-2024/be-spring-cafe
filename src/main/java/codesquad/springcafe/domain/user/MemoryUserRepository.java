package codesquad.springcafe.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
