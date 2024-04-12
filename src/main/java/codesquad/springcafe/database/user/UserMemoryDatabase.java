package codesquad.springcafe.database.user;

import codesquad.springcafe.model.User;
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
    public Optional<User> findBy(String nickname) {
        return findAll().stream()
                .filter(user -> user.hasSameNickname(nickname))
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
}
