package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;

public interface UserRepository extends DbRepository<User> {
    void update(User user);
    User getById(String userId);

}
