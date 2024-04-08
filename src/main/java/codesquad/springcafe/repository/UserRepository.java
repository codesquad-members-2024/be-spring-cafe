package codesquad.springcafe.repository;

import codesquad.springcafe.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final List<User> users = new ArrayList<>();

    public void saveUser(User user) {
        users.add(user);
        log.debug("user saved: {}", user.getUserId());
    }

    public List<User> findAllUsers() {
        return users;
    }
}
