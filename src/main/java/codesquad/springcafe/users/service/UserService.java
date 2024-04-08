package codesquad.springcafe.users.service;

import model.UserVO;
import model.User;

import java.util.*;

public interface UserService {
    void createUser(UserVO userVO);

    List<User> getAllUsers();
}
