package codesquad.springcafe.database.user;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDatabase {
    User add(User user);

    Optional<User> findBy(String nickname);

    List<User> findAll();

    void update(User user);

    void clear();
}
