package codesquad.springcafe.DB;

import codesquad.springcafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDatabase {
    private static final List<User> users = new ArrayList<>();

    public static void saveUser(User user) {
        users.add(user);
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static int getUserSize(){
        return users.size();
    }

    public static Optional<User> getUser(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

}
