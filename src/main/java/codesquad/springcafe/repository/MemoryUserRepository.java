package codesquad.springcafe.repository;

import codesquad.springcafe.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        store.put(user.getUserId(), user);
        logger.info("saved user={}", user);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
