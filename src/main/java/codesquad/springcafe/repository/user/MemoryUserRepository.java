package codesquad.springcafe.repository.user;

import codesquad.springcafe.model.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> users;

    public MemoryUserRepository() {
        users = new LinkedHashMap<>();
    }

    @Override
    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> getById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void modify(User modifiedUser) {
        users.put(modifiedUser.getUserId(), modifiedUser);
    }
}
