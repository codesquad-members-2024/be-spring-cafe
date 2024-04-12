package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User create(User user);

    User update(User user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}