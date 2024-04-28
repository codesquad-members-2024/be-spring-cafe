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
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return findAllUsers().stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }

    @Override
    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        Optional<User> optionalUser = findByUserId(userId);
        optionalUser.ifPresent(user -> user.update(userUpdateDto));
    }
}