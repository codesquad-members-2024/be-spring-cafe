package codesquad.springcafe.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryDatabase implements UserDatabase{

    private final static Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserId(), user);
    }

    @Override
    public List<User> getUserList() {
        return userMap.values().stream().toList();
    }

    @Override
    public User getUser(String userId) {
        return userMap.get(userId);
    }

    @Override
    public boolean isExistUser(String userId) {
        return getUser(userId) != null;
    }
}
