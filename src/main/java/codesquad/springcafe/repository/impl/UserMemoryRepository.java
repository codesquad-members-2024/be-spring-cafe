package codesquad.springcafe.repository.impl;

import codesquad.springcafe.dto.UpdatedUser;
import codesquad.springcafe.dto.User;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemoryRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserMemoryRepository.class);
    private static List<User> users = new ArrayList<>();

    @Override
    public User addUser(User user) {
        users.add(user);
        logger.debug(user.toString() + " MemoryDB 저장 완료");
        return user;
    }

    @Override
    public User findUserByUserID(String userId) throws UserNotFoundException {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();

        // 사용자를 찾지 못한 경우 UserNotFoundException 예외를 던진다.
        return optionalUser.orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User updateUser(String userId, UpdatedUser updatedUser) throws Exception {
        User findUser = findUserByUserID(userId);
        findUser.updateUser(updatedUser);
        return findUser;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return users;
    }
}
