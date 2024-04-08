package codesquad.springcafe.database;

import codesquad.springcafe.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemoryDatabase implements UserDatabase {
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private long sid = 0L;

    @Override
    public User save(User user) {
        user.setJoinDate(LocalDate.now());
        user.setSid(++sid);
        store.put(sid, user);
        return user;
    }

    @Override
    public Optional<User> findBySid(Long sid) {
        return Optional.ofNullable(store.get(sid));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
        sid = 0L;
    }
}
