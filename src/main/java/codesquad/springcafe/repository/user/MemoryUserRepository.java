package codesquad.springcafe.repository.user;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);
    private static final List<User> users = new ArrayList<>();

    @Override
    public void createUser(UserDto userDto) {
        User user = userDto.toEntity();
        users.add(user);
        logger.debug("사용자 생성: {}", user.toDto());
    }

    @Override
    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        User user = findByUserId(userId).get();
        user.update(userUpdateDto);
        logger.debug("정보 업데이트: {}", userUpdateDto);
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return findAllUsers().stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }
}