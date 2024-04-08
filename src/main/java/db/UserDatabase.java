package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class UserDatabase {
    private static final Logger logger = LoggerFactory.getLogger(UserDatabase.class);

    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
        logger.debug("User [{}] Added , Total Users : {}", user, users.size());
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}
