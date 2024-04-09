package codesquad.springcafe;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static final Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public void add(User user) {
        store.put(sequence++, user);
        logger.info("SAVED USER : {}", user.toString());
    }
}