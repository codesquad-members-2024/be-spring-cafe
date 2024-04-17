package codesquad.springcafe.database.user;

import codesquad.springcafe.model.User;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryDatabase implements UserDatabase {
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public User add(User user) {
        user.setId(++id);

        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return findAll().stream()
                .filter(user -> user.hasSameNickname(nickname))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.hasSameEmail(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @PostConstruct
    private void createTestUser() {
        User user1 = new User("sangchu@gmail.com", "상추", "123");
        User user2 = new User("baechu@gmail.com", "배추", "123");
        add(user1);
        add(user2);
    }
}
