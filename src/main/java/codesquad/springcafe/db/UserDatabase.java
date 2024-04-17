package codesquad.springcafe.db;

import codesquad.springcafe.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;


@Component
public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        logger.debug("User [{}] Added , Total Users : {}", user, users.size());
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public Optional<User> findUserById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }
}
