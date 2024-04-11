package codesquad.springcafe.users.repository;

import model.User;
import model.UserData;
import model.UserUpdateData;

import java.util.ArrayList;

public interface UserRepository {
    void createUser(UserData userData);

    ArrayList<User> getAllUsers();

    User findUserById(String userId);

    void updateUser(String userId, UserUpdateData updateData);
}
