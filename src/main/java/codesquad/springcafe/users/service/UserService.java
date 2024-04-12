package codesquad.springcafe.users.service;

import model.UserData;
import model.User;
import model.UserUpdateData;

import java.util.*;

public interface UserService {
    void createUser(UserData userData);

    ArrayList<User> getAllUsers();

    User findUserById(String userId);

    void updateUser(String userId, UserUpdateData updateData);
}
