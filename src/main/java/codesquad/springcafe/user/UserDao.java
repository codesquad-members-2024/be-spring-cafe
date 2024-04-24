package codesquad.springcafe.user;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    void save(final User user);

    Collection<User> getAllUsers();

    Optional<User> findUser(String userId);

    void update(User user);
}
