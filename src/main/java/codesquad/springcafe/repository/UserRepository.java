package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;

import java.util.List;

public interface UserRepository {

    void saveUser(User user);

    List<User> findAllUsers();

    User findUserById(String userId);

    void clear();

    void update(User user);

}
