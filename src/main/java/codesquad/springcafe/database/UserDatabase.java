package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import java.util.List;

public interface UserDatabase {
    void save(User user);

    User findByUserId(String userId);

    List<User> findAll();

    void clear();

}
