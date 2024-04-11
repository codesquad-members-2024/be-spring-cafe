package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {

    private static final Map<String, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
