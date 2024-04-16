package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final List<User> users = new ArrayList<>();

    @Override
    public User createUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(String userId, UserUpdateDto userUpdateDto) {
        User user = findByUserId(userId).get();
        user.update(userUpdateDto);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }
}