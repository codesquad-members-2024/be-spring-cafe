package codesquad.springcafe.repository.user;

import codesquad.springcafe.exception.db.UserNotFoundException;
import codesquad.springcafe.model.ListUser;
import codesquad.springcafe.model.SessionUser;
import codesquad.springcafe.model.UpdatedUser;
import codesquad.springcafe.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public Optional<User> findUserByUserId(String userId) throws UserNotFoundException {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        return optionalUser;
    }

    @Override
    public String updateUser(String userId, UpdatedUser updatedUser) throws UserNotFoundException {
        User findUser = findUserByUserId(userId).get();
        findUser.updateUser(updatedUser);
        return userId;
    }

    @Override
    public String deleteUser(String userId) {
        return null;
    }

    @Override
    public List<ListUser> findAllUser() {
        return null;
    }

    @Override
    public Optional<SessionUser> findSessionUserByUserId(String userId) {
        return Optional.empty();
    }
}
