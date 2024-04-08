package codesquad.springcafe.repository;

import codesquad.springcafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> users;
    private final AtomicLong sequence;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
        sequence = new AtomicLong();
    }

    public void save(User user) {
        users.put(sequence.getAndIncrement(), user);
    }

    public Map<Long, User> getUsers() {
        return users;
    }
}
