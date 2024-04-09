package codesquad.springcafe.user.repository;

import codesquad.springcafe.user.User;
import codesquad.springcafe.user.UserDTO;

import java.util.List;

public interface UserRepository {

    void addUser(User user) throws IllegalArgumentException;

    void update(User user);
    User findUserById(String id);
    List<UserDTO> findAll();
}
