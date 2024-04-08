package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemoryDatabase implements UserDatabase {
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        store.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String userId) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void clear() {

    }
}
