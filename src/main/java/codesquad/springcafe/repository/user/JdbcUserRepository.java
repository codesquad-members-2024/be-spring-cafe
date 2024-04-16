package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(String userId, UserUpdateDto userUpdateDto) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.empty();
    }
}