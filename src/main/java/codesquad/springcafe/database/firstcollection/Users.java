package codesquad.springcafe.database.firstcollection;

import codesquad.springcafe.user.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {

    private final Map<String, User> users = new HashMap<>();

    public void put(User user) {
        users.put(user.getUserId(), user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
