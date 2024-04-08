package codesquad.springcafe.database;

import codesquad.springcafe.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private static final List<User> users = new ArrayList<>();

    public static void saveUser(User user) {
        users.add(user);
    }

    public static List<User> findAllUsers() {
        return users;
    }
}
