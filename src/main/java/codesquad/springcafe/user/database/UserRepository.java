package codesquad.springcafe.user.database;

import codesquad.springcafe.user.User;

import java.util.List;

public interface UserRepository {

     List<User> findAll();

    void save(User user);

    User findByUserId(String userId);

    void updateUser(User user, String userId);
}
