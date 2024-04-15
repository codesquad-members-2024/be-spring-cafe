package codesquad.springcafe.database;

import codesquad.springcafe.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryDatabase implements UserDatabase{

    private final static Map<String, User> userMap = new ConcurrentHashMap<>();

    public void addUser(User user) {
        userMap.put(user.getUserId(), user);
    }

    public List<User> getUserList() {
        return userMap.values().stream().toList();
    }

    public User getUser(String userId) {
        return userMap.get(userId);
    }
}
