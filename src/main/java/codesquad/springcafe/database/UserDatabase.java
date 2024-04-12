package codesquad.springcafe.database;

import codesquad.springcafe.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase {

    private final static Map<String, User> userMap = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        userMap.put(user.getUserId(), user);
    }
}
