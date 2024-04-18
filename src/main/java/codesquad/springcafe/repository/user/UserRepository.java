package codesquad.springcafe.repository.user;

import codesquad.springcafe.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> getById(String userId);

    List<User> getAll();

    void modify(User modifiedUser);
}
