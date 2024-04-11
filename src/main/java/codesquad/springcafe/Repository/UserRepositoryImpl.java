package codesquad.springcafe.Repository;

import codesquad.springcafe.Domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository; //컨테이너가 등록

@Repository
public class UserRepositoryImpl implements UserRepository {

    List<User> userDB = new ArrayList<>();

    @Override
    public User save(User user) {
        userDB.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return userDB.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDB.stream()
            .filter(user -> user.getEmail().equals(email))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return userDB;
    }
}