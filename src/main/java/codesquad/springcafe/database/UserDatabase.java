package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDatabase {
    User save(User user);

    Optional<User> findByNickname(String nickname);

    List<User> findAll();

    void clear();
}
