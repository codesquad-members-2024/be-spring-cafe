package codesquad.springcafe.users.service;

import model.UserData;
import model.User;

import java.util.*;

public interface UserService {
    void createUser(UserData userData);

    ArrayList<User> getAllUsers();

    Optional<User> findUserById(String userId);
}
