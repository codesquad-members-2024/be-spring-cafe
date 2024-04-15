package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final Logger log = LoggerFactory.getLogger(UserMemoryRepository.class);
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public void saveUser(User user) {
        users.put(user.getUserId(), user);
        log.debug("user saved: {}", user.getUserId());
    }

    public List<User> findAllUsers() {
        return new ArrayList<User>(users.values());
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public void update(User user) {
        String userId = user.getUserId();
        users.put(userId, user);
    }

}
