package codesquad.springcafe.database.firstcollection;

import codesquad.springcafe.user.domain.User;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Users {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public void put(User user) {
        String name = URLDecoder.decode(user.getName(), StandardCharsets.UTF_8);
        users.put(name, user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUser(String name) {
        String decodeName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return Optional.ofNullable(users.get(decodeName));
    }
}
