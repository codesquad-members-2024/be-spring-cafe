package codesquad.springcafe.repository;

import codesquad.springcafe.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryUserRepository implements UserRepository{
    private static final Logger logger = LoggerFactory.getLogger(MemoryUserRepository.class);
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong();


    @Override
    public User save(User user) {
        Long createdId = sequence.incrementAndGet();
        user.setId(createdId);
        store.put(createdId, user);
        logger.info("saved user={}", user.getId());

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }
}
