package codesquad.springcafe.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final List<User> users = new ArrayList<>();

    public void save(final User user) {
        users.add(user);
        log.debug("유저 수 : {}", users.size());
    }
}
