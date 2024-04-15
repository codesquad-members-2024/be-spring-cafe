package codesquad.springcafe.domain.user;

public interface UserRepository {

    void save(User user);
    void update(User user);
    User findByUserId(String userId);
}
