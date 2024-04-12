package codesquad.springcafe.users.repository;

import model.user.User;
import model.user.UserData;
import model.user.UserUpdateData;

import java.util.ArrayList;

public interface UserRepository {
    void createUser(UserData userData);

    ArrayList<User> getAllUsers();

    User findUserById(String userId);

    void updateUser(String userId, UserUpdateData updateData);
}
