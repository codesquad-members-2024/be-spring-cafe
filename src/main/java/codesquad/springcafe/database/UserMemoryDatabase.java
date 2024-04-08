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
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        user.setJoinDate(LocalDate.now());
        store.put(user.getNickname(), user);
        return user;
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return Optional.ofNullable(store.get(nickname));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
