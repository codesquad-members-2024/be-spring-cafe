package codesquad.springcafe.repository.user;

import codesquad.springcafe.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Repository
public class MemoryUserRepository implements UserRepository {
    private static final List<User> users = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);

    @Override
    public User save(User user) {
        users.add(user);
        logger.info("SAVED USER : {}", user.toString());
        return user;
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }
}