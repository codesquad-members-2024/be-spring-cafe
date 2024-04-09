package codesquad.springcafe.users.service;

import db.UserDatabase;
import model.User;
import model.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserManagementService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    @Override
    public void createUser(UserData userData) {
        String email = userData.getEmail();
        String userId = userData.getUserId();
        String password = userData.getPassword();

        User user = new User(email, userId, password);
        logger.debug("User Created : {}", user);

        UserDatabase.addUser(user);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return UserDatabase.getAllUsers();
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return UserDatabase.findUserById(userId);
    }
}
