package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Map<Long, User> users = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public User createUser(UserDto userDto) {
        User user = userDto.toEntity();
        user.setId(++id);
        users.put(id, user);
        return user;
    }

    @Override
    public User updateUser(String userId, UserUpdateDto userUpdateDto) {
        User user = findUserByUserId(userId).get();
        user.update(userUpdateDto);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        return findAllUsers().stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }
}