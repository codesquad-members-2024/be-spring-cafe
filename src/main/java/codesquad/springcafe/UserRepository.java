package codesquad.springcafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
}