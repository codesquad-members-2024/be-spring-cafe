package codesquad.springcafe.DB;

import codesquad.springcafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Database {
    private static final List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static User getUser(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
