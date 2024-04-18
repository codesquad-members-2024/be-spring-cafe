package codesquad.springcafe.database.firstcollection;

import codesquad.springcafe.user.domain.User;

import java.util.*;

public class Users {

    private final Map<String, User> users = new HashMap<>();

    public void put(User user) {
        users.put(user.getUserId(), user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUser(String name) {
        return Optional.ofNullable(users.get(name));
    }
}
