package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static final List<User> users = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public void add(User user) {
        users.add(user);
        logger.info("SAVED USER : {}", user.toString());
    }

    public List<User> users() {
        return Collections.unmodifiableList(users);
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }
}